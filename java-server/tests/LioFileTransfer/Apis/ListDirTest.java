package LioFileTransfer.Apis;

import LioFileTransfer.Config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListDirTest {

    private static Config config;

    @BeforeAll
    static void initConfig() {
        config = new Config();
    }


    @Test
    void handleRequestSuccess() {
        ListDir listDir = new ListDir(config);
        JsonObject json = new JsonObject();
        json.addProperty("dir", "/");
        ApiResponse response = listDir.handleRequest(json);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(response));
    }

    @Test
    void handleRequestArgument() {
        ListDir listDir = new ListDir(config);
        JsonObject json = new JsonObject();
        json.addProperty("", "/");
        ApiResponse response = listDir.handleRequest(json);

        JsonObject excpected = new JsonObject();
        excpected.addProperty("error", "argument");
        assertEquals(excpected, response.getBody());
    }
}