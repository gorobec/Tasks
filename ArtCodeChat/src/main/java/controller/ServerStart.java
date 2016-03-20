package controller;

import java.io.IOException;

/**
 * Created by gorobec on 20.03.16.
 */
public class ServerStart {
    public static void main(String[] args) {
        try {
            new NewServer().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

