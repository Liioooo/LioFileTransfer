package LioFileTransfer.Handlers;

import LioFileTransfer.Apis.*;
import LioFileTransfer.Authentication;
import LioFileTransfer.Main;
import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/*
100 success
101 Method not Post
102 not application/json
103 Api not found
104 invalid json
110 not logged in, invalid token
 */

public class ApiHandler implements HttpHandler {

    private Set<Api> apis;

    public ApiHandler() {
        apis = new HashSet<>();
        apis.add(new Login(Main.config));
        apis.add(new ListDir(Main.config));
        apis.add(new Move(Main.config));
        apis.add(new Copy(Main.config));
        apis.add(new Delete(Main.config));
        apis.add(new Create(Main.config));
        apis.add(new Rename(Main.config));
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Server", "LioFileTransferServer (Java)");
        exchange.getResponseHeaders().set("Content-Type", "application/json");

        if(!exchange.getRequestMethod().equals("POST")) {
            HandlerHelpers.sendError(exchange, 101);
            return;
        }

        if(!exchange.getResponseHeaders().getFirst("Content-Type").equals("application/json")) {
            HandlerHelpers.sendError(exchange, 102);
            return;
        }

        String apiPath = exchange.getRequestURI().getPath().replace("/api/", "");

        for(Api api : apis) {
            if(api.getApiPath().equals(apiPath)) {
                if(api.needsLogin()) {
                    if(!Authentication.isLoggedIn(exchange)) {
                        HandlerHelpers.sendError(exchange, 110);
                        return;
                    }
                }

                String requestString = new String(exchange.getRequestBody().readAllBytes());
                JsonObject jsonObject;
                try {
                    jsonObject = new JsonParser().parse(requestString).getAsJsonObject();
                } catch (JsonSyntaxException e) {
                    HandlerHelpers.sendError(exchange, 104);
                    return;
                }

                ApiResponse apiResponse = api.handleRequest(jsonObject);
                if(!apiResponse.getHeaders().isEmpty()) {
                    apiResponse.getHeaders().forEach((k, v) -> exchange.getResponseHeaders().set(k, v));
                }
                JsonObject responseJson = new JsonObject();
                responseJson.addProperty("status", 100);
                responseJson.add("response", apiResponse.getBody());
                HandlerHelpers.sendData(exchange, responseJson);
                return;
            }
        }
        HandlerHelpers.sendError(exchange, 103);
    }
}
