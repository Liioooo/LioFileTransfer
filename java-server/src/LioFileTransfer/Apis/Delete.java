package LioFileTransfer.Apis;

import LioFileTransfer.Config;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

public class Delete extends Api {

    public Delete(Config config) {
        super(config);
    }

    @Override
    public String getApiPath() {
        return "delete";
    }

    @Override
    public boolean needsLogin() {
        return true;
    }

    @Override
    public ApiResponse handleRequest(JsonObject requestBody) {
        JsonObject responseJson = new JsonObject();

        var paramCheckResult = ApiHelpers.checkParams(requestBody, "toDelete");
        if(paramCheckResult != null) return paramCheckResult;

        File file = new File(config.getConfigItemString("documentRoot") + requestBody.get("toDelete").getAsString());

        if(!file.exists()) {
            responseJson.addProperty("error", "doesntExists");
            return new ApiResponse(responseJson);
        }

        try {
            if(file.isDirectory()) {
                Files.walk(file.toPath())
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
                Thread.sleep(50);
            } else {
                file.delete();
            }
            responseJson.addProperty("error", "null");
        } catch (IOException e) {
            responseJson.addProperty("error", "deleting");
        } catch (InterruptedException e) {
            responseJson.addProperty("error", "null");
        }

        return new ApiResponse(responseJson);
    }
}
