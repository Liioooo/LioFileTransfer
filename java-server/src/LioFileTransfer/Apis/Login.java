package LioFileTransfer.Apis;

import LioFileTransfer.Authentication;
import com.google.gson.JsonObject;

import java.util.HashMap;

public class Login implements Api {

    @Override
    public String getApiPath() {
        return "login";
    }

    @Override
    public boolean needsLogin() {
        return false;
    }

    @Override
    public ApiResponse handleRequest(JsonObject requestBody) {
        JsonObject responseJson = new JsonObject();
        if(!requestBody.has("username") || !requestBody.has("password")) {
            responseJson.addProperty("error", "argument");
            return new ApiResponse(responseJson);
        }
        String token = Authentication.login(requestBody.get("username").getAsString(), requestBody.get("password").getAsString());
        if(token == null) {
            responseJson.addProperty("error", "invalid");
            return new ApiResponse(responseJson);
        }
        responseJson.addProperty("error", "null");
        responseJson.addProperty("auth", true);
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Set-Cookie", "token=" + token + "; HttpOnly");
        return new ApiResponse(responseJson, headers);
    }
}
