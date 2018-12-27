package LioFileTransfer.Handlers;

import LioFileTransfer.Authentication;
import LioFileTransfer.Main;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UploadHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Server", "LioFileTransferServer (Java)");

        if(!Authentication.isLoggedIn(exchange)) {
            HandlerHelpers.sendError(exchange, 110);
            return;
        }

        String path = exchange.getRequestURI().getPath().replace("/upload", "");
        Files.write(Paths.get(Main.config.getConfigItemString("documentRoot") + path), exchange.getRequestBody().readAllBytes());

        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("status", 100);
        responseJson.add("response", new JsonObject());
        HandlerHelpers.sendData(exchange, responseJson);
    }
}
