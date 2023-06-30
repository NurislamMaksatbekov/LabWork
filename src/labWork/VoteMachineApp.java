package labWork;
import com.sun.net.httpserver.HttpExchange;
import entity.User;
import server.BasicServer;
import server.ContentType;
import util.FileService;
import util.Utils;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class VoteMachineApp extends BasicServer {


    public VoteMachineApp(String host, int port) throws IOException {
        super(host, port);
        registerGet("/register", this::registerModuleGet);
        registerPost("/register", this::registerModulePOST);
        registerGet("/notExists", this::notExists);
    }

    private void registerModuleGet(HttpExchange exchange) {

    }


    private void registerModulePOST(HttpExchange exchange) {
        List<User> user = FileService.readUser();
        String raw = getBody(exchange);
        Map<String, String> parsed = Utils.parseUrlEncoded(raw, "&");
        try {
            String name = parsed.get("name");
            String mail = parsed.get("email");
            String password = parsed.get("password");

            if (name.isBlank() || mail.isBlank() || password.isBlank()) {
                redirect303(exchange, "notExists");
                return;
            }
            boolean check = true;
            for (User employee : user) {
                if (mail.equals(employee.getEmail())) {
                    check = false;
                    break;
                }
            }
            if (check) {
                int votes= 0;
                int id = 0;

                User newUser = new User(0,name, mail, password, votes);
                user.add(newUser);
                FileService.writeFile(user);
                redirect303(exchange, "/login");
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            redirect303(exchange, "/notExists");
        }
    }



    private void notExists(HttpExchange exchange) {

    }
}
