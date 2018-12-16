package LioFileTransfer.Apis;

import LioFileTransfer.Config;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CopyTest {

    private static Config config;

    @BeforeAll
    static void initConfig() {
        config = new Config();
    }

    @Test
    void handleRequestSuccess() {
        var copy = new Copy(config);
        var json = new JsonObject();
        json.addProperty("src", "/test/kek");
        json.addProperty("dest", "/test1");
        ApiResponse response = copy.handleRequest(json);

        var expected = new JsonObject();
        expected.addProperty("error", "null");
        assertEquals(expected, response.getBody());
    }

    @Test
    void handleRequestArgument() {
        var copy = new Copy(config);
        var json = new JsonObject();
        json.addProperty("src", "/test/test");
        ApiResponse response = copy.handleRequest(json);

        var expected = new JsonObject();
        expected.addProperty("error", "argument");
        assertEquals(expected, response.getBody());
    }
}