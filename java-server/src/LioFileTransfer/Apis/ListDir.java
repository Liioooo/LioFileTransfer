package LioFileTransfer.Apis;

import LioFileTransfer.Config;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ListDir extends Api {

    public ListDir(Config config) {
        super(config);
    }

    @Override
    public String getApiPath() {
        return "listDir";
    }

    @Override
    public boolean needsLogin() {
        return true;
    }

    @Override
    public ApiResponse handleRequest(JsonObject requestBody) {
        JsonObject responseJson = new JsonObject();

        if(!requestBody.has("dir")) {
            responseJson.addProperty("error", "argument");
            return new ApiResponse(responseJson);
        }

        try {
            JsonArray jsonArray = new JsonArray();
            Files.list(new File(config.getConfigItemString("documentRoot") + requestBody.get("dir").getAsString()).toPath())
                    .map(f -> {
                        JsonObject file = new JsonObject();
                        file.addProperty("file", f.getFileName().toString());
                        file.addProperty("type", f.toFile().isDirectory() ? "dir" : "file");
                        return file;
                    })
                    .forEach(jsonArray::add);
            responseJson.addProperty("error", "null");
            responseJson.add("listing", jsonArray);
        } catch (IOException e) {
            responseJson.addProperty("error", "reading");
        }
        return new ApiResponse(responseJson);
    }
}