package LioFileTransfer.Apis;

import LioFileTransfer.Config;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Copy extends Api {

    public Copy(Config config) {
        super(config);
    }

    @Override
    public String getApiPath() {
        return "copy";
    }

    @Override
    public boolean needsLogin() {
        return true;
    }

    @Override
    public ApiResponse handleRequest(JsonObject requestBody) {
        JsonObject responseJson = new JsonObject();

        var paramCheckResult = ApiHelpers.checkParams(requestBody, "src", "dest");
        if(paramCheckResult != null) return paramCheckResult;

        File src = new File(config.getConfigItemString("documentRoot") + requestBody.get("src").getAsString());
        File dest = new File(config.getConfigItemString("documentRoot") + requestBody.get("dest").getAsString() + src.getPath().substring(src.getPath().lastIndexOf("\\")));

        if(dest.exists()) {
            responseJson.addProperty("error", "destDoesExists");
            return new ApiResponse(responseJson);
        }

        if(new File(config.getConfigItemString("documentRoot") + requestBody.get("dest").getAsString()).isFile()) {
            responseJson.addProperty("error", "destIsFile");
            return new ApiResponse(responseJson);
        }

        if(!src.exists()) {
            responseJson.addProperty("error", "srcDoesntExist");
            return new ApiResponse(responseJson);
        }

        try {
            copy(src, dest);
            responseJson.addProperty("error", "null");
        } catch (IOException e) {
            responseJson.addProperty("error", "copy");
        }

        return new ApiResponse(responseJson);

    }

    private void copy(File src, File dest) throws IOException {
        if(src.isDirectory()) {
            new File(dest.getPath()).mkdirs();
            for (File f : src.listFiles()) {
                copy(f, new File(dest.getPath() + f.getPath().substring(f.getPath().lastIndexOf("\\"))));
            }
        } else {
            Files.copy(src.toPath(), dest.toPath());
        }
    }
}
