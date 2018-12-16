package LioFileTransfer.Apis;

import LioFileTransfer.Config;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {

    private static Config config;

    @BeforeAll
    static void initConfig() {
        config = new Config();
    }

    @Test
    void handleRequestSuccess() {
        var move = new Move(config);
        var json = new JsonObject();
        json.addProperty("src", "/test/kek");
        json.addProperty("dest", "/test1");
        ApiResponse response = move.handleRequest(json);

        var expected = new JsonObject();
        expected.addProperty("error", "null");
        assertEquals(expected, response.getBody());
    }

    @Test
    void handleRequestArgument() {
        var move = new Move(config);
        var json = new JsonObject();
        json.addProperty("src", "/test/test");
        ApiResponse response = move.handleRequest(json);

        var expected = new JsonObject();
        expected.addProperty("error", "argument");
        assertEquals(expected, response.getBody());
    }
}