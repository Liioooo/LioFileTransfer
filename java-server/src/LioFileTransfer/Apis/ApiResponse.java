package LioFileTransfer.Apis;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class ApiResponse {

    private JsonObject body;
    private Map<String, String> headers;

    public ApiResponse(JsonObject body) {
        this.body = body;
        this.headers = new HashMap<>();
    }

    public ApiResponse(JsonObject body, Map<String, String> headers) {
        this.body = body;
        this.headers = headers;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public JsonObject getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
