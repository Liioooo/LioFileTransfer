package LioFileTransfer.Apis;

import LioFileTransfer.Config;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RenameTest {

    private static Config config;

    @BeforeAll
    static void initConfig() {
        config = new Config();
    }

    @Test
    void handleRequestDirSuccess() {
        var rename = new Rename(config);
        var json = new JsonObject();
        json.addProperty("toRename", "/test/keko");
        json.addProperty("newName", "/test/kekomat");
        ApiResponse response = rename.handleRequest(json);

        var expected = new JsonObject();
        expected.addProperty("error", "null");
        assertEquals(expected, response.getBody());
    }

    @Test
    void handleRequestFileSuccess() {
        var rename = new Rename(config);
        var json = new JsonObject();
        json.addProperty("toRename", "/test/test.txt");
        json.addProperty("newName", "/test/testokek.txt");
        ApiResponse response = rename.handleRequest(json);

        var expected = new JsonObject();
        expected.addProperty("error", "null");
        assertEquals(expected, response.getBody());
    }

    @Test
    void handleRequestArgument() {
        var rename = new Rename(config);
        var json = new JsonObject();
        json.addProperty("toRename", "/test/test.txt");
        ApiResponse response = rename.handleRequest(json);

        var expected = new JsonObject();
        expected.addProperty("error", "argument");
        assertEquals(expected, response.getBody());
    }
}