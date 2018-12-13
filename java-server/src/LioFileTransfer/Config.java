package LioFileTransfer;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Config {

    private Map configMap;

    public Config() {
        try {
            String configStr = new String(Files.readAllBytes(Paths.get("config.json")));
            JsonObject jsonObject = new JsonParser().parse(configStr).getAsJsonObject();
            configMap = parseConfig(jsonObject);
        } catch (IOException e) {
            System.err.println("Config File not found: config.json");
            System.exit(-1);
        } catch (JsonParseException | IllegalStateException e) {
            System.err.println("Invalid config.json file!");
            System.exit(-1);
        }
    }

    private static Map<String, String> parseConfig(JsonObject jsonObject) {
        Map<String, String> configMap = new HashMap<>();
        jsonObject.keySet().forEach(key -> {
            configMap.put(key, jsonObject.get(key).getAsString());
        });
        return configMap;
    }

    public String getConfigItemString(String key) {
        return (String) configMap.get(key);
    }

    public int getConfigItemInteger(String key) {
        try {
            return Integer.parseInt((String) configMap.get(key));
        } catch (NumberFormatException e) {
            throw new InvalidTypeError("Item Type is not Integer");
        }
    }
}
