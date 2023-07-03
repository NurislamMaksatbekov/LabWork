package labWork;

import com.sun.net.httpserver.HttpExchange;
import dataModel.CandidateDataModel;
import entity.Candidate;
import entity.User;

import dataModel.CandidatesDataModel;
import server.BasicServer;
import util.FileService;
import util.Utils;

import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.Map;

public class VoteMachineApp extends BasicServer {
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
    }

    private Optional<Candidate> findCandidateByName(String name) {
        return candidates.stream()
                .filter(e -> e.getName().equalsIgnoreCase(name))
                .findFirst();
    }
    private Map<String, String> getParsedBody(HttpExchange exchange) {
        return Utils.parseUrlEncoded(getBody(exchange), "&");
    }

    private void votePost(HttpExchange exchange) {
        var parsed = getParsedBody(exchange);
        String name = parsed.get("name");

        Optional<Candidate> candidate = findCandidateByName(name);
        if (candidate.isPresent()) {
            redirect303(exchange, "/thankYou?name="+name);
        } else {
            redirect303(exchange, "/errorBook");
        }
    }


    private void thankYouGet(HttpExchange exchange) {
        String query = getQueryParams(exchange);
        Map<String, String> params = Utils.parseUrlEncoded(query, "&");
        String name = params.getOrDefault("name", null);
        renderTemplate(exchange, "thankyou.ftlh", getCandidateDataModel(name));
    }

    private CandidateDataModel getCandidateDataModel(String name) {
        Optional<Candidate> candidate = candidates.stream()
                .filter(c -> c.getName().equals(name))
                .findFirst();
        return new CandidateDataModel(candidate);
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
                FileService.writeFile(users);
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

}
