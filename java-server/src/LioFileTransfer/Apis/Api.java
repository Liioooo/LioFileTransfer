package LioFileTransfer.Apis;

import com.google.gson.JsonObject;

public interface Api {

    public String getApiPath();

    public boolean needsLogin();

    public ApiResponse handleRequest(JsonObject requestBody);
}
