package LioFileTransfer.Apis;

import LioFileTransfer.Main;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ListDir implements Api {

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
            Files.list(new File(Main.config.getConfigItemString("documentRoot") + requestBody.get("dir").getAsString()).toPath())
                    .map(f -> {
                        JsonObject file = new JsonObject();
                        file.addProperty("file", f.getFileName().toString());
                        return file;
                    })
                    .forEach(jsonArray::add);
            responseJson.add("listing", jsonArray);
        } catch (IOException e) {
            responseJson.addProperty("error", "reading");
        }
        return new ApiResponse(responseJson);
    }
}