package LioFileTransfer.Apis;

import com.google.gson.JsonObject;

public class Login implements Api {

    @Override
    public String getApiPath() {
        return "login";
    }

    @Override
    public boolean needsLogin() {
        return true;
    }

    @Override
    public ApiResponse handleRequest(JsonObject requestBody) {
        System.out.println(requestBody);

        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("test", "test");
        return new ApiResponse(responseJson);
    }
}
