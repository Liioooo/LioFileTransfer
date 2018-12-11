package LioFileTransfer;


import LioFileTransfer.Handlers.ApiHandler;
import LioFileTransfer.Handlers.StaticFileHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class Main {

    private static Config config;
    private static HttpServer server;

    public static void main(String[] args) {
        System.out.println("Starting LioFileTransfer Server...");
        config = new Config();
        createServer();
    }

    private static void createServer() {
        try {
            System.out.println("Creating Webserver...");
            int port = config.getConfigItemInteger("webserverPort");
            server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/", new StaticFileHandler());
            server.createContext("/api", new ApiHandler());
            server.start();
            System.out.println("Successfully started Webserver at port" + port);
        } catch (InvalidTypeError e) {
            System.err.println("Invalid 'webserverPort' in config.json");
            System.exit(-1);
        } catch (IOException e) {
            System.err.println("Error while creating Webserver");
            System.exit(-1);
        }
    }
}
