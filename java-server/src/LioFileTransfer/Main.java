package LioFileTransfer;

import com.google.gson.Gson;

public class Main {

    public static void main(String[] args) {
        System.out.println("test");
        Gson g = new Gson();
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
