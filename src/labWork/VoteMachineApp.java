package labWork;

import com.sun.net.httpserver.HttpExchange;
import entity.User;

import dataModel.CandidateDataModel;
import server.BasicServer;
import util.FileService;
import util.Utils;

import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.Map;

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

    private void loginGet(HttpExchange exchange) {
        renderTemplate(exchange, "login.ftlh", null);
    }

    private void loginPost(HttpExchange exchange) {
        String raw = getBody(exchange);
        Map<String, String> parsed = Utils.parseUrlEncoded(raw, "&");
        try {
            List<User> userList = FileService.readUser();
            String mail = parsed.get("email");
            String password = parsed.get("password");
            boolean check = false;
            if (mail.isBlank() || password.isBlank()) {
                redirect303(exchange, "notLoginExists");
                return;
            }
            for (User user : userList) {
                if (mail.equals(user.getEmail()) && password.equals(user.getPassword())) {
                    cookie(exchange, mail);
                    check = true;
                    break;
                }
            }
            if (check) {
                redirect303(exchange,"/register");
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            redirect303(exchange, "notLoginExists");
        }
    }

    private CandidateDataModel getCandidatesDataModel() {
        return new CandidateDataModel();
    }

}
