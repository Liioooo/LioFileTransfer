package LioFileTransfer.Apis;

import LioFileTransfer.Config;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Create extends Api {

    public Create(Config config) {
        super(config);
    }

    @Override
    public String getApiPath() {
        return "create";
    }

    @Override
    public boolean needsLogin() {
        return true;
    }

    @Override
    public ApiResponse handleRequest(JsonObject requestBody) {
        JsonObject responseJson = new JsonObject();

        var paramCheckResult = ApiHelpers.checkParams(requestBody, "toCreate");
        if(paramCheckResult != null) return paramCheckResult;

        File file = new File(config.getConfigItemString("documentRoot") + requestBody.get("toCreate").getAsString());

        if(file.exists()) {
            responseJson.addProperty("error", "alreadyExists");
            return new ApiResponse(responseJson);
        }

        try {
            if(file.getPath().split("[.]").length > 1) {
                Files.createFile(file.toPath());
            } else {
                Files.createDirectory(file.toPath());
            }
            responseJson.addProperty("error", "null");
        } catch (IOException e) {
            responseJson.addProperty("error", "creating");
        }

        return new ApiResponse(responseJson);
    }
}
