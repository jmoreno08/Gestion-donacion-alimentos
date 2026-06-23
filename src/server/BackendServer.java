package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import dao.BeneficiarioDAO;
import dao.DonanteDAO;
import dao.EntregaDAO;
import dao.ProductoDAO;
import dto.BeneficiarioDTO;
import dto.DonanteDTO;
import dto.EntregaDTO;
import dto.ProductoDTO;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

public class BackendServer {

    private static final Gson GSON = new GsonBuilder()
        .registerTypeAdapter(LocalDate.class, (com.google.gson.JsonSerializer<LocalDate>)
            (src, typeOfSrc, context) -> context.serialize(src.toString()))
        .registerTypeAdapter(LocalDate.class, (com.google.gson.JsonDeserializer<LocalDate>)
            (json, typeOfT, context) -> LocalDate.parse(json.getAsString()))
        .create();

    public static void start() throws IOException, InterruptedException {
        int port = Integer.parseInt(getEnv("APP_PORT", "8080"));
        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", port), 0);
        server.createContext("/", BackendServer::root);
        server.createContext("/health", BackendServer::health);
        server.createContext("/api/donantes", BackendServer::donantes);
        server.createContext("/api/productos", BackendServer::productos);
        server.createContext("/api/beneficiarios", BackendServer::beneficiarios);
        server.createContext("/api/entregas", BackendServer::entregas);
        server.setExecutor(Executors.newFixedThreadPool(8));
        server.start();

        System.out.println("Backend HTTP iniciado en el puerto " + port);
        new CountDownLatch(1).await();
    }

    private static void root(HttpExchange exchange) throws IOException {
        if (isOptions(exchange)) {
            noContent(exchange);
            return;
        }
        if (!isMethod(exchange, "GET")) {
            methodNotAllowed(exchange);
            return;
        }

        sendJson(exchange, 200, "{"
            + "\"name\":\"gestion-donacion-alimentos\","
            + "\"endpoints\":["
            + "\"/health\","
            + "\"/api/donantes\","
            + "\"/api/productos\","
            + "\"/api/beneficiarios\","
            + "\"/api/entregas\""
            + "]"
            + "}");
    }

    private static void health(HttpExchange exchange) throws IOException {
        if (isOptions(exchange)) {
            noContent(exchange);
            return;
        }
        if (!isMethod(exchange, "GET")) {
            methodNotAllowed(exchange);
            return;
        }

        sendJson(exchange, 200, "{\"status\":\"ok\"}");
    }

    private static void donantes(HttpExchange exchange) throws IOException {
        DonanteDAO dao = new DonanteDAO();
        Integer id = pathId(exchange, "/api/donantes");
        if (id != null && id < 1) {
            badRequest(exchange, "id invalido");
            return;
        }

        switch (exchange.getRequestMethod().toUpperCase()) {
            case "OPTIONS" -> noContent(exchange);
            case "GET" -> {
                if (id == null) sendJson(exchange, 200, GSON.toJson(dao.consultarTodos()));
                else sendFoundOrNotFound(exchange, dao.consultarPorId(id));
            }
            case "POST" -> {
                DonanteDTO dto = readJson(exchange, DonanteDTO.class);
                dto.setIdDonante(0);
                sendMutation(exchange, dao.insertar(dto), 201);
            }
            case "PUT" -> {
                if (id == null) {
                    badRequest(exchange, "id requerido");
                    return;
                }
                DonanteDTO dto = readJson(exchange, DonanteDTO.class);
                dto.setIdDonante(id);
                sendMutation(exchange, dao.actualizar(dto), 200);
            }
            case "DELETE" -> {
                if (id == null) {
                    badRequest(exchange, "id requerido");
                    return;
                }
                sendMutation(exchange, dao.eliminar(id), 200);
            }
            default -> methodNotAllowed(exchange);
        }
    }

    private static void productos(HttpExchange exchange) throws IOException {
        ProductoDAO dao = new ProductoDAO();
        Integer id = pathId(exchange, "/api/productos");
        if (id != null && id < 1) {
            badRequest(exchange, "id invalido");
            return;
        }

        switch (exchange.getRequestMethod().toUpperCase()) {
            case "OPTIONS" -> noContent(exchange);
            case "GET" -> {
                if (id == null) sendJson(exchange, 200, GSON.toJson(dao.consultarTodos()));
                else sendFoundOrNotFound(exchange, dao.consultarPorId(id));
            }
            case "POST" -> {
                ProductoDTO dto = readJson(exchange, ProductoDTO.class);
                dto.setIdProducto(0);
                sendMutation(exchange, dao.insertar(dto), 201);
            }
            case "PUT" -> {
                if (id == null) {
                    badRequest(exchange, "id requerido");
                    return;
                }
                ProductoDTO dto = readJson(exchange, ProductoDTO.class);
                dto.setIdProducto(id);
                sendMutation(exchange, dao.actualizar(dto), 200);
            }
            case "DELETE" -> {
                if (id == null) {
                    badRequest(exchange, "id requerido");
                    return;
                }
                sendMutation(exchange, dao.eliminar(id), 200);
            }
            default -> methodNotAllowed(exchange);
        }
    }

    private static void beneficiarios(HttpExchange exchange) throws IOException {
        BeneficiarioDAO dao = new BeneficiarioDAO();
        Integer id = pathId(exchange, "/api/beneficiarios");
        if (id != null && id < 1) {
            badRequest(exchange, "id invalido");
            return;
        }

        switch (exchange.getRequestMethod().toUpperCase()) {
            case "OPTIONS" -> noContent(exchange);
            case "GET" -> {
                if (id == null) sendJson(exchange, 200, GSON.toJson(dao.consultarTodos()));
                else sendFoundOrNotFound(exchange, dao.consultarPorId(id));
            }
            case "POST" -> {
                BeneficiarioDTO dto = readJson(exchange, BeneficiarioDTO.class);
                dto.setIdBeneficiario(0);
                sendMutation(exchange, dao.insertar(dto), 201);
            }
            case "PUT" -> {
                if (id == null) {
                    badRequest(exchange, "id requerido");
                    return;
                }
                BeneficiarioDTO dto = readJson(exchange, BeneficiarioDTO.class);
                dto.setIdBeneficiario(id);
                sendMutation(exchange, dao.actualizar(dto), 200);
            }
            case "DELETE" -> {
                if (id == null) {
                    badRequest(exchange, "id requerido");
                    return;
                }
                sendMutation(exchange, dao.eliminar(id), 200);
            }
            default -> methodNotAllowed(exchange);
        }
    }

    private static void entregas(HttpExchange exchange) throws IOException {
        EntregaDAO dao = new EntregaDAO();
        Integer id = pathId(exchange, "/api/entregas");
        if (id != null && id < 1) {
            badRequest(exchange, "id invalido");
            return;
        }

        switch (exchange.getRequestMethod().toUpperCase()) {
            case "OPTIONS" -> noContent(exchange);
            case "GET" -> {
                if (id == null) sendJson(exchange, 200, GSON.toJson(dao.consultarTodos()));
                else sendFoundOrNotFound(exchange, dao.consultarPorId(id));
            }
            case "POST" -> {
                EntregaDTO dto = readJson(exchange, EntregaDTO.class);
                dto.setIdEntrega(0);
                sendMutation(exchange, dao.insertar(dto), 201);
            }
            case "PUT" -> {
                if (id == null) {
                    badRequest(exchange, "id requerido");
                    return;
                }
                EntregaDTO dto = readJson(exchange, EntregaDTO.class);
                dto.setIdEntrega(id);
                sendMutation(exchange, dao.actualizar(dto), 200);
            }
            case "DELETE" -> {
                if (id == null) {
                    badRequest(exchange, "id requerido");
                    return;
                }
                sendMutation(exchange, dao.eliminar(id), 200);
            }
            default -> methodNotAllowed(exchange);
        }
    }

    private static <T> T readJson(HttpExchange exchange, Type type) throws IOException {
        try {
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            if (body.isBlank()) throw new JsonParseException("JSON vacio");
            return GSON.fromJson(body, type);
        } catch (RuntimeException e) {
            badRequest(exchange, "JSON invalido: " + e.getMessage());
            throw new IOException(e);
        }
    }

    private static Integer pathId(HttpExchange exchange, String basePath) {
        String path = exchange.getRequestURI().getPath();
        if (path.equals(basePath) || path.equals(basePath + "/")) return null;
        if (!path.startsWith(basePath + "/")) return -1;

        String id = path.substring(basePath.length() + 1);
        if (id.contains("/")) return -1;
        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void sendFoundOrNotFound(HttpExchange exchange, Object value) throws IOException {
        if (value == null) sendJson(exchange, 404, "{\"error\":\"no_encontrado\"}");
        else sendJson(exchange, 200, GSON.toJson(value));
    }

    private static void sendMutation(HttpExchange exchange, boolean ok, int successStatus) throws IOException {
        if (ok) sendJson(exchange, successStatus, "{\"ok\":true}");
        else sendJson(exchange, 404, "{\"ok\":false}");
    }

    private static boolean isMethod(HttpExchange exchange, String method) {
        return method.equalsIgnoreCase(exchange.getRequestMethod());
    }

    private static boolean isOptions(HttpExchange exchange) {
        return isMethod(exchange, "OPTIONS");
    }

    private static void badRequest(HttpExchange exchange, String message) throws IOException {
        sendJson(exchange, 400, "{\"error\":\"" + escape(message) + "\"}");
    }

    private static void methodNotAllowed(HttpExchange exchange) throws IOException {
        sendJson(exchange, 405, "{\"error\":\"method_not_allowed\"}");
    }

    private static void noContent(HttpExchange exchange) throws IOException {
        addCorsHeaders(exchange);
        exchange.sendResponseHeaders(204, -1);
    }

    private static void sendJson(HttpExchange exchange, int status, String json) throws IOException {
        byte[] body = json.getBytes(StandardCharsets.UTF_8);
        addCorsHeaders(exchange);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        exchange.sendResponseHeaders(status, body.length);
        try (OutputStream response = exchange.getResponseBody()) {
            response.write(body);
        }
    }

    private static void addCorsHeaders(HttpExchange exchange) {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
    }

    private static String escape(String value) {
        if (value == null) return "";
        return value
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r");
    }

    private static String getEnv(String nombre, String valorPorDefecto) {
        String valor = System.getenv(nombre);
        return valor == null || valor.isBlank() ? valorPorDefecto : valor;
    }
}
