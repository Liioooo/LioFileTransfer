package LioFileTransfer.Apis;

import LioFileTransfer.Config;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateTest {

    private static Config config;

    @BeforeAll
    static void initConfig() {
        config = new Config();
    }

    @Test
    void handleRequestDirSuccess() {
        var create = new Create(config);
        var json = new JsonObject();
        json.addProperty("toCreate", "/test/keko");
        ApiResponse response = create.handleRequest(json);

        var expected = new JsonObject();
        expected.addProperty("error", "null");
        assertEquals(expected, response.getBody());
    }

    @Test
    void handleRequestFileSuccess() {
        var create = new Create(config);
        var json = new JsonObject();
        json.addProperty("toCreate", "/test/test.txt");
        ApiResponse response = create.handleRequest(json);

        var expected = new JsonObject();
        expected.addProperty("error", "null");
        assertEquals(expected, response.getBody());
    }

    @Test
    void handleRequestArgument() {
        var create = new Create(config);
        var json = new JsonObject();
        ApiResponse response = create.handleRequest(json);

        var expected = new JsonObject();
        expected.addProperty("error", "argument");
        assertEquals(expected, response.getBody());
    }
}