package LioFileTransfer.Apis;

import LioFileTransfer.Config;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeleteTest {

    private static Config config;

    @BeforeAll
    static void initConfig() {
        config = new Config();
    }

    @Test
    void handleRequestDirSuccess() {
        var del = new Delete(config);
        var json = new JsonObject();
        json.addProperty("toDelete", "/test/kek");
        ApiResponse response = del.handleRequest(json);

        var expected = new JsonObject();
        expected.addProperty("error", "null");
        assertEquals(expected, response.getBody());
    }

    @Test
    void handleRequestFileSuccess() {
        var del = new Delete(config);
        var json = new JsonObject();
        json.addProperty("toDelete", "/test/test.txt");
        ApiResponse response = del.handleRequest(json);

        var expected = new JsonObject();
        expected.addProperty("error", "null");
        assertEquals(expected, response.getBody());
    }

    @Test
    void handleRequestArgument() {
        var del = new Delete(config);
        var json = new JsonObject();
        ApiResponse response = del.handleRequest(json);

        var expected = new JsonObject();
        expected.addProperty("error", "argument");
        assertEquals(expected, response.getBody());
    }

}