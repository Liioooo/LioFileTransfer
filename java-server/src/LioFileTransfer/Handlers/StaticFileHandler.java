package LioFileTransfer.Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StaticFileHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Server", "LioFileTransferServer (Java)");

        String requestPath = exchange.getRequestURI().getPath();
        if(requestPath.equals("/")) {
            requestPath = "/index.html";
        }
        try {
            byte[] file = Files.readAllBytes(Paths.get("webDocs" + requestPath));
            exchange.getResponseHeaders().set("Content-Type", getContentType(requestPath));
            exchange.sendResponseHeaders(200, file.length);
            OutputStream os = exchange.getResponseBody();
            os.write(file);
            os.close();
        } catch (IOException e) {
            send404(exchange, requestPath);
        }
    }

    private void send404(HttpExchange exchange, String requestPath) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "text/plain");
        String response = "404 File not found!\n" + requestPath + " was not found";
        exchange.sendResponseHeaders(404, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String getContentType(String requestPath) {
        if(requestPath.endsWith(".html")) {
            return "text/html";
        } else if(requestPath.endsWith(".css")) {
            return "text/css";
        } else if(requestPath.endsWith(".js")) {
            return "text/javascript";
        } else if(requestPath.endsWith(".json")) {
            return "application/json";
        } else if(requestPath.endsWith(".svg")) {
            return "image/svg+xml";
        } else if(requestPath.endsWith(".jpg") || requestPath.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if(requestPath.endsWith(".png")) {
            return "image/png";
        } else {
            return "";
        }
    }
}
