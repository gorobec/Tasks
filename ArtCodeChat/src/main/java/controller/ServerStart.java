package controller;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by gorobec on 20.03.16.
 */
public class ServerStart {
    public static void main(String[] args) {
        try {
            new Server().start();
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}

