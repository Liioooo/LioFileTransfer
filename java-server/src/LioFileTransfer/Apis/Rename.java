package LioFileTransfer.Apis;

import LioFileTransfer.Config;
import com.google.gson.JsonObject;

import java.io.File;

public class Rename extends Api {

    public Rename(Config config) {
        super(config);
    }

    @Override
    public String getApiPath() {
        return "rename";
    }

    @Override
    public boolean needsLogin() {
        return true;
    }

    @Override
    public ApiResponse handleRequest(JsonObject requestBody) {
        JsonObject responseJson = new JsonObject();

        var paramCheckResult = ApiHelpers.checkParams(requestBody, "toRename", "newName");
        if(paramCheckResult != null) return paramCheckResult;

        File file = new File(config.getConfigItemString("documentRoot") + requestBody.get("toRename").getAsString());

        if(!file.exists()) {
            responseJson.addProperty("error", "doesntExists");
            return new ApiResponse(responseJson);
        }

        file.renameTo(new File(config.getConfigItemString("documentRoot") + requestBody.get("newName").getAsString()));
        responseJson.addProperty("error", "null");

        return new ApiResponse(responseJson);
    }
}
