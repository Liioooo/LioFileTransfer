package LioFileTransfer;

import com.sun.net.httpserver.HttpExchange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Authentication {

    private static List<String> authenticatedTokens = new ArrayList<>();

    public static String login(String username, String password) {
        if(!Main.config.getConfigItemString("username").equals(username)) {
            return null;
        }
        if(!Main.config.getConfigItemString("password").equals(password)) {
            return null;
        }
        return generateToken();
    }

    public static void logout(String token) {
        authenticatedTokens.remove(token);
    }

    private static String generateToken() {
        final String CHARS = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ234567890!@#$";
        final Random random = new Random();
        StringBuilder token = new StringBuilder(32);
        for (int i = 0; i < 32; i++) {
            token.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        String tokenStr = token.toString();
        authenticatedTokens.add(tokenStr);
        return tokenStr;
    }

    public static boolean isLoggedIn(HttpExchange exchange) {
        HashMap<String, String> cookies = new HashMap<>();
        for(String cookie : exchange.getRequestHeaders().getFirst("Cookie").split("; ")) {
            String[] cookieParts = cookie.split("=");
            cookies.put(cookieParts[0], cookieParts[1]);
        }
        return (cookies.containsKey("token") && authenticatedTokens.contains(cookies.get("token")));
    }
}
