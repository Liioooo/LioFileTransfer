package LioFileTransfer.Apis;

import LioFileTransfer.Config;
import com.google.gson.JsonObject;

public abstract class Api {

    protected Config config;

    public Api(Config config) {
        this.config = config;
    }

    abstract public String getApiPath();

    abstract public boolean needsLogin();

    abstract public ApiResponse handleRequest(JsonObject requestBody);
}