package labWork;

import com.sun.net.httpserver.HttpExchange;
import dataModel.CandidateDataModel;
import entity.Candidate;
import entity.User;

import dataModel.CandidatesDataModel;
import server.BasicServer;
import server.ResponseCodes;
import util.FileService;
import util.Utils;

import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.Map;

public class VoteMachineApp extends BasicServer {
    private double totalVotes;
    private final List<User> users = Collections.synchronizedList(new ArrayList<>());
    private final List<Candidate> candidates = Collections.synchronizedList(new ArrayList<>());

    public VoteMachineApp(String host, int port) throws IOException {
        super(host, port);
        this.users.addAll(FileService.readUser());
        this.candidates.addAll(FileService.readCandidatesFile());
        registerGet("/", this::candidatesHandler);
        registerGet("/thankYou", this::thankYouGet);
        registerGet("/login", this::loginGet);
        registerPost("/login", this::loginPost);
        registerGet("/register", this::registerModuleGet);
        registerPost("/register", this::registerModulePost);
        registerGet("/notExists", this::notExists);
        registerGet("/incorrectLogin", this::errorLogin);
        registerPost("/vote", this::votePost);
        registerGet("/votes", this::votesGet);
    }

    private void votesGet(HttpExchange exchange) {
        renderTemplate(exchange, "votes.ftlh", getCandidatesDataModel());
    }


    private Optional<Candidate> findCandidateByName(String name) {
        return candidates.stream()
                .filter(e -> e.getName().equalsIgnoreCase(name))
                .findAny();
    }
    private Map<String, String> getParsedBody(HttpExchange exchange) {
        return Utils.parseUrlEncoded(getBody(exchange), "&");
    }

    private void votePost(HttpExchange exchange) {
        var parsed = getParsedBody(exchange);
        String name = parsed.get("name");

        Optional<Candidate> candidate = findCandidateByName(name);
        if (candidate.isPresent()) {
            candidate.get().setVotes(candidate.get().getVotes() + 1);
            setTotalVotes(getTotalVotes() + 1);
            candidate.get().setPercent(candidate.get().getVotes() / getTotalVotes() * 100);
            for (Candidate c : candidates) {
                if (c.getName().equals(candidate.get().getName())) {
                    c.setPercent(candidate.get().getPercent());
                    break;
                }
            }
            FileService.writeCandidates(candidates);
            redirect303(exchange, "/thankYou?name="+name);
        } else {
            respond404(exchange);
        }
    }



    private void thankYouGet(HttpExchange exchange) {
        String query = getQueryParams(exchange);
        Map<String, String> params = Utils.parseUrlEncoded(query, "&");
        String name = params.getOrDefault("name", null);
        renderTemplate(exchange, "thankyou.ftlh", getCandidateDataModel(name));
    }

    public CandidateDataModel getCandidateDataModel(String name) {
        for (Candidate candidate : candidates) {
            if (candidate.getName().equalsIgnoreCase(name)) {
                return new CandidateDataModel(candidate);
            }
        }
        return null;
    }

    private void errorLogin(HttpExchange exchange) {
        renderTemplate(exchange, "incorrectLogin.ftlh", null);
    }

    private void candidatesHandler(HttpExchange exchange) {
        renderTemplate(exchange, "candidates.ftlh", getCandidatesDataModel());
    }

    private void registerModuleGet(HttpExchange exchange) {
        renderTemplate(exchange, "register.ftlh", null);
    }


    private void registerModulePost(HttpExchange exchange) {
        String raw = getBody(exchange);
        Map<String, String> parsed = Utils.parseUrlEncoded(raw, "&");
        try {
            String name = parsed.get("name");
            String email = parsed.get("email");
            String password = parsed.get("password");

            Optional<User> findUserByEmail = users.stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst();
            if (findUserByEmail.isPresent() || checkString(name) || checkString(password)) {
                throw new IOException();
            } else {
                int votes = 0;
                int id = users.size() + 1;
                User newUser = new User(id, name, email, password, votes);
                users.add(newUser);
                FileService.writeUsers(users);
                redirect303(exchange, "/login");
            }
        } catch (IOException e) {
            redirect303(exchange, "/notExists");
        }
    }


    private void notExists(HttpExchange exchange) {
        renderTemplate(exchange, "notExists.ftlh", null);
    }

    private void loginGet(HttpExchange exchange) {
        renderTemplate(exchange, "login.ftlh", null);
    }

    private void loginPost(HttpExchange exchange) {
        String raw = getBody(exchange);
        Map<String, String> parsed = Utils.parseUrlEncoded(raw, "&");
        String email = parsed.get("email");
        String password = parsed.get("password");
        Optional<User> findUserByEmail = users.stream().filter(u -> u.getEmail().equalsIgnoreCase(email) && u.getPassword().equalsIgnoreCase(password)).findFirst();
        if (findUserByEmail.isPresent()) {
            cookie(exchange, email);
            redirect303(exchange, "/");
        } else {
            redirect303(exchange, "/incorrectLogin");
        }
    }

    private CandidatesDataModel getCandidatesDataModel() {
        return new CandidatesDataModel();
    }

    public double getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(double totalVotes) {
        this.totalVotes = totalVotes;
    }
}
