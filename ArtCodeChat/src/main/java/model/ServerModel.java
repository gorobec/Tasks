package model;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gorobec on 20.03.16.
 */
public class ServerModel {
    private static final int PORT = 5555;
    private ServerSocket serverSocket;
    private List<User> users;
    private List<Message> history;

    public ServerModel(){
        try {
            this.serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.err.println("Can't connect to the port");
            e.printStackTrace();
        }
        history = new ArrayList<>();

//        new Gson().toJson(history);
    }
}
