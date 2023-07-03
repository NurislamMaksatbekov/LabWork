package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class BasicServer {

    private final Configuration freemarker = initFreeMarker();

    private final HttpServer server;
    // путь к каталогу с файлами, которые будет отдавать сервер по запросам клиентов
    private final String dataDir = "data";
    private Map<String, RouteHandler> routes = new HashMap<>();

    protected BasicServer(String host, int port) throws IOException {
        server = createServer(host, port);
        registerCommonHandlers();
    }

    private static Configuration initFreeMarker() {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
            cfg.setDirectoryForTemplateLoading(new File("data"));

            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);
            cfg.setFallbackOnNullLoopVariable(false);
            return cfg;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void renderTemplate(HttpExchange exchange, String templateFile, Object dataModel) {
        try {
            Template temp = freemarker.getTemplate(templateFile);


            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            try (OutputStreamWriter writer = new OutputStreamWriter(stream)) {

                temp.process(dataModel, writer);
                writer.flush();

                var data = stream.toByteArray();

                sendByteData(exchange, ResponseCodes.OK, ContentType.TEXT_HTML, data);
            }
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    private static String makeKey(String method, String route) {
        route = ensureStartsWithSlash(route);
        return String.format("%s %s", method.toUpperCase(), route);
    }

    private static String ensureStartsWithSlash(String route) {
        if (route.startsWith("."))
            return route;
        return route.startsWith("/") ? route : "/" + route;
    }

    private static String makeKey(HttpExchange exchange) {
        var method = exchange.getRequestMethod();
        var path = exchange.getRequestURI().getPath();
        if (path.endsWith("/") && path.length() > 1) {
            path = path.substring(0, path.length() - 1);
        }
        var index = path.lastIndexOf(".");
        var extOrPath = index != -1 ? path.substring(index).toLowerCase() : path;
        return makeKey(method, extOrPath);
    }

    private static void setContentType(HttpExchange exchange, ContentType type) {
        exchange.getResponseHeaders().set("Content-Type", String.valueOf(type));
    }

    private static HttpServer createServer(String host, int port) throws IOException {
        var msg = "Starting server on http://%s:%s/%n";
        System.out.printf(msg, host, port);
        var address = new InetSocketAddress(host, port);
        return HttpServer.create(address, 50);
    }

    private void registerCommonHandlers() {
        server.createContext("/", this::handleIncomingServerRequests);
        registerFileHandler(".css", ContentType.TEXT_CSS);
        registerFileHandler(".html", ContentType.TEXT_HTML);
        registerFileHandler(".ftlh", ContentType.TEXT_HTML);
        registerFileHandler(".jpg", ContentType.IMAGE_JPEG);
        registerFileHandler(".jpeg", ContentType.IMAGE_JPEG);
        registerFileHandler(".png", ContentType.IMAGE_PNG);
    }



    protected final void registerGenericHandler(String method, String route, RouteHandler handler) {
        getRoutes().put(makeKey(method, route), handler);
    }
    protected final void registerGet(String route, RouteHandler handler) {
        registerGenericHandler("GET", route, handler);
    }

    protected final void registerPost(String route, RouteHandler handler) {
        registerGenericHandler("POST", route, handler);
    }

    protected final void registerFileHandler(String fileExt, ContentType type) {
        registerGet(fileExt, exchange -> sendFile(exchange, makeFilePath(exchange), type));
    }

    protected final Map<String, RouteHandler> getRoutes() {
        return routes;
    }

    protected final void sendFile(HttpExchange exchange, Path pathToFile, ContentType contentType) {
        try {
            if (Files.notExists(pathToFile)) {
                respond404(exchange);
                return;
            }
            var data = Files.readAllBytes(pathToFile);
            sendByteData(exchange, ResponseCodes.OK, contentType, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Path makeFilePath(HttpExchange exchange) {
        return makeFilePath(exchange.getRequestURI().getPath());
    }

    protected Path makeFilePath(String... s) {
        return Path.of(dataDir, s);
    }

    protected final void sendByteData(HttpExchange exchange, ResponseCodes responseCode,
                                      ContentType contentType, byte[] data) throws IOException {
        try (var output = exchange.getResponseBody()) {
            setContentType(exchange, contentType);
            exchange.sendResponseHeaders(responseCode.getCode(), 0);
            output.write(data);
            output.flush();
        }
    }

    protected void respond404(HttpExchange exchange) {
        try {
            var data = "404 Not found".getBytes();
            sendByteData(exchange, ResponseCodes.NOT_FOUND, ContentType.TEXT_PLAIN, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleIncomingServerRequests(HttpExchange exchange) {
        var route = getRoutes().getOrDefault(makeKey(exchange), this::respond404);
        route.handle(exchange);
    }

    public static String getContentType(HttpExchange exchange) {
        return exchange.getRequestHeaders()
                .getOrDefault("Content-Type", List.of(""))
                .get(0);
    }

    protected String getBody(HttpExchange exchange) {
        InputStream input = exchange.getRequestBody();
        InputStreamReader isr = new InputStreamReader(input, StandardCharsets.UTF_8);
        try (BufferedReader reader = new BufferedReader(isr)) {
            return reader.lines().collect(Collectors.joining(""));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    protected void redirect303(HttpExchange exchange, String path) {
        try {
            exchange.getResponseHeaders().add("Location", path);
            exchange.sendResponseHeaders(ResponseCodes.REDIRECT.getCode(), 0);
            exchange.getResponseBody().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getCookies(HttpExchange exchange){
        return exchange.getRequestHeaders()
                .getOrDefault("Cookie", List.of(""))
                .get(0);
    }
    protected void setCookie(HttpExchange exchange, Cookie cookie){
        exchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
    }

    protected String getQueryParams(HttpExchange exchange) {
             String query = exchange.getRequestURI().getQuery();
        return Objects.nonNull(query) ? query : "";
    }

    protected void logout(HttpExchange exchange) {
        String cookieString = exchange.getRequestHeaders().getFirst("Cookie");
        if (cookieString != null) {
            Map<String, String> cookies = Cookie.parse(cookieString);
            if (cookies.containsKey("email")) {
                String expiredCookie = "email=; expires=Thu, 01 Jan 1970 00:00:00 GMT";
                exchange.getResponseHeaders().add("Set-Cookie", expiredCookie);
            }
        }
    }

    protected boolean isUserAuthenticated(HttpExchange exchange) {
        String cookie = getCookies(exchange);
        Map<String, String> parse = Cookie.parse(cookie);
        String emailUser = parse.get("email");
        return emailUser != null && isValidUser(emailUser);
    }

    protected boolean isValidUser(String email) {
        return true;
    }

protected boolean checkString (String str){
        return str.isBlank() || str.isEmpty();
}

    public final void start() {
        server.start();
    }
}
