package labWork;

import com.sun.net.httpserver.HttpExchange;
import server.BasicServer;
import server.Cookie;
import util.FileService;
import util.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoteMachineApp extends BasicServer {
    public VoteMachineApp(String host, int port) throws IOException {
        super(host, port);
        registerGet("/login", this::loginGet);
        registerPost("/login", this::loginPost);
    }

    private void loginPost(HttpExchange exchange) {
            String raw = getBody(exchange);
            Map<String, String> parsed = Utils.parseUrlEncoded(raw, "&");

            String email = parsed.get("email");
            String password = parsed.get("password");

            if (user.stream().anyMatch(e -> e.getEmail().equals(email) && e.getPassword().equals(password))) {
                Map<String, Object> data = new HashMap<>();
                cookie = Cookie.make("email", email);

                String cookieString = getCookies(exchange);
                Map<String, String> cookies = Cookie.parse(cookieString);
                cookie.setMaxAge(getMaxAge());
                cookie.setHttpOnly(true);

                setCookie(exchange, cookie);
                data.put("cookies", cookies);

                redirect303(exchange, "/profile?email=" + email);
            } else {
                redirect303(exchange, "/incorrectData");
            }
    }

    private void loginGet(HttpExchange exchange) {
        renderTemplate(exchange, "login.ftlh", null);
    }
}
