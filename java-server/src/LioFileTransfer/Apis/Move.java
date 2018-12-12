package LioFileTransfer.Apis;

import LioFileTransfer.Main;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

public class Move implements Api {

    @Override
    public String getApiPath() {
        return "move";
    }

    @Override
    public boolean needsLogin() {
        return true;
    }

    @Override
    public ApiResponse handleRequest(JsonObject requestBody) {
        JsonObject responseJson = new JsonObject();

        if(!requestBody.has("src") || !requestBody.has("dest")) {
            responseJson.addProperty("error", "argument");
            return new ApiResponse(responseJson);
        }
        File src = new File(Main.config.getConfigItemString("documentRoot") + requestBody.get("src").getAsString());
        File dest = new File(Main.config.getConfigItemString("documentRoot") + requestBody.get("dest").getAsString() + src.getPath().substring(src.getPath().lastIndexOf("\\")));

        if(new File(Main.config.getConfigItemString("documentRoot") + requestBody.get("dest").getAsString()).isFile()) {
            responseJson.addProperty("error", "destIsFile");
            return new ApiResponse(responseJson);
        }

        if(!src.exists()) {
            responseJson.addProperty("error", "srcDoesntExist");
            return new ApiResponse(responseJson);
        }
        if(dest.exists()) {
            responseJson.addProperty("error", "doesExists");
            return new ApiResponse(responseJson);
        }
        try {
            move(src, dest);
            Files.walk(src.toPath())
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);

            responseJson.addProperty("error", "null");
        } catch (IOException e) {
            responseJson.addProperty("error", "copy");
        }
        return new ApiResponse(responseJson);
    }

    private void move(File src, File dest) throws IOException {
        if(src.isDirectory()) {
            new File(dest.getPath()).mkdirs();
            for (File f : src.listFiles()) {
                move(f, new File(dest.getPath() + f.getPath().substring(f.getPath().lastIndexOf("\\"))));
            }
        } else {
            Files.move(src.toPath(), dest.toPath());
        }
    }
}
