package LioFileTransfer.Apis;

import LioFileTransfer.Authentication;
import LioFileTransfer.Config;
import com.google.gson.JsonObject;

import java.util.HashMap;

public class Login extends Api {

    public Login(Config config) {
        super(config);
    }

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

        var paramCheckResult = ApiHelpers.checkParams(requestBody, "username", "password");
        if(paramCheckResult != null) return paramCheckResult;

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
