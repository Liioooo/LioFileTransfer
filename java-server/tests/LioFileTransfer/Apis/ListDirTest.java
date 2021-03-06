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
        var listDir = new ListDir(config);
        var json = new JsonObject();
        json.addProperty("dir", "/");
        ApiResponse response = listDir.handleRequest(json);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(response));
    }

    @Test
    void handleRequestArgument() {
        var listDir = new ListDir(config);
        var json = new JsonObject();
        json.addProperty("", "/");
        ApiResponse response = listDir.handleRequest(json);

        var expected = new JsonObject();
        expected.addProperty("error", "argument");
        assertEquals(expected, response.getBody());
    }
}