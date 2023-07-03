package labWork;

import com.sun.net.httpserver.HttpExchange;
import entity.User;

import dataModel.CandidateDataModel;
import server.BasicServer;
import util.FileService;
import util.Utils;

import java.io.IOException;
import java.util.*;

public class VoteMachineApp extends BasicServer {
    private final List<User> users = Collections.synchronizedList(new ArrayList<>());

    public VoteMachineApp(String host, int port) throws IOException {
        super(host, port);
        this.users.addAll(FileService.readUser());
        registerGet("/", this::candidatesHandler);
        registerGet("/login", this::loginGet);
//        registerPost("/login", this::loginPost);
        registerGet("/register", this::registerModuleGet);
        registerPost("/register", this::registerModulePost);
        registerGet("/notExists", this::notExists);
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
            }else {
                int votes = 0;
                int id = users.size() + 1;
                User newUser = new User(id, name, email, password, votes);
                users.add(newUser);
                FileService.writeFile(users);
                redirect303(exchange, "/login");
            }
        }catch (IOException e){
            redirect303(exchange, "/notExists");
        }
    }


    private void notExists(HttpExchange exchange) {
        renderTemplate(exchange, "notExists.ftlh", null);
    }

//    private void loginPost(HttpExchange exchange) {
//            String raw = getBody(exchange);
//            Map<String, String> parsed = Utils.parseUrlEncoded(raw, "&");
//
//            String email = parsed.get("email");
//            String password = parsed.get("password");
//
//            if (users.stream().anyMatch(e -> e.getEmail().equals(email) && e.getPassword().equals(password))) {
//                Map<String, Object> data = new HashMap<>();
//                cookie = Cookie.make("email", email);
//
//                String cookieString = getCookies(exchange);
//                Map<String, String> cookies = Cookie.parse(cookieString);
//                cookie.setMaxAge(getMaxAge());
//                cookie.setHttpOnly(true);
//
//                setCookie(exchange, cookie);
//                data.put("cookies", cookies);
//
//                redirect303(exchange, "/profile?email=" + email);
//            } else {
//                redirect303(exchange, "/incorrectData");
//            }
//    }

    private void loginGet(HttpExchange exchange) {
        renderTemplate(exchange, "login.ftlh", null);
        registerGet("/", this::candidatesHandler);
    }

    private void candidatesHandler(HttpExchange exchange) {
        renderTemplate(exchange, "candidates.ftlh", getCandidatesDataModel());
    }

    private CandidateDataModel getCandidatesDataModel() {
        return new CandidateDataModel();
    }

}
