package LioFileTransfer.Handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class HandlerHelpers {

    public static void send404(HttpExchange exchange, String requestPath) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "text/plain");
        String response = "404 File not found!\n" + requestPath + " was not found";
        exchange.sendResponseHeaders(404, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public static void sendError(HttpExchange exchange, int errorCode) throws IOException {
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("status", errorCode);
        responseJson.add("response", new JsonObject());
        sendData(exchange, responseJson);
    }

    public static void sendData(HttpExchange exchange, JsonObject responseJson) throws IOException{
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String response = gson.toJson(responseJson);
        byte[] responseBytes = response.getBytes();
        exchange.sendResponseHeaders(200, responseBytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseBytes);
        os.close();
    }
}
