package LioFileTransfer.Apis;

import com.google.gson.JsonObject;

public class ApiHelpers {

    static public ApiResponse checkParams(JsonObject request, String... requiredArgs) {
        JsonObject responseJson = new JsonObject();

        for(String arg : requiredArgs) {
            if(!request.has(arg)) {
                responseJson.addProperty("error", "argument");
                return new ApiResponse(responseJson);
            }
        }
        return null;
    }
}
