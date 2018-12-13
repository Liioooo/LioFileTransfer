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
        Move move = new Move(config);
        JsonObject json = new JsonObject();
        json.addProperty("src", "/test/kek");
        json.addProperty("dest", "/test1");
        ApiResponse response = move.handleRequest(json);

        JsonObject excpected = new JsonObject();
        excpected.addProperty("error", "null");
        assertEquals(excpected, response.getBody());
    }

    @Test
    void handleRequestArgument() {
        ListDir listDir = new ListDir(config);
        JsonObject json = new JsonObject();
        json.addProperty("src", "/test/test");
        ApiResponse response = listDir.handleRequest(json);

        JsonObject excpected = new JsonObject();
        excpected.addProperty("error", "argument");
        assertEquals(excpected, response.getBody());
    }
}