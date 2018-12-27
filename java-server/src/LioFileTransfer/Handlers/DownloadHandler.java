package LioFileTransfer.Handlers;

import LioFileTransfer.Authentication;
import LioFileTransfer.Main;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DownloadHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Server", "LioFileTransferServer (Java)");

        String requestPath = exchange.getRequestURI().getPath();
        if(!Authentication.isLoggedIn(exchange)) {
            HandlerHelpers.sendError(exchange, 110);
            return;
        }
        try {
            byte[] file = Files.readAllBytes(Paths.get(Main.config.getConfigItemString("documentRoot") + requestPath.replace("/download", "")));
            exchange.sendResponseHeaders(200, file.length);
            OutputStream os = exchange.getResponseBody();
            os.write(file);
            os.close();
        } catch (IOException e) {
            HandlerHelpers.send404(exchange, requestPath);
        }
    }

}
