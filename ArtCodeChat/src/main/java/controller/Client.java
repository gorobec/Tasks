package controller;


import model.NotEqualPasswordException;
import model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private final int PORT = 5555;
    private final String IP = "127.0.0.1";
    private Socket client;
    private boolean isLogIn;

    public Client() {
    }
    public void start(){
        try {
            do {
                client = new Socket(IP, PORT);
            } while (!client.isBound());
        } catch (IOException e) {
            System.err.println("Can't connect to the server");
            System.exit(-1);
        }
        System.out.println("Connected to the server!");
         Thread receiveMessage = new Thread(() -> {
           try(BufferedReader  in = new BufferedReader
                    (new InputStreamReader(client.getInputStream()))){
               while (true) {

                   String serverMessage = null;
                   try {
                       serverMessage = in.readLine();
                   } catch (IOException e) {
                            /*NOP*/
                   }
                   System.out.println((char) 27 + "[34m" + serverMessage);
               }
           } catch (IOException e) {
               e.printStackTrace();
        }
        });
        receiveMessage.start();
    }

    public void writeMessage(String message) {
        try(PrintWriter out = new PrintWriter(client.getOutputStream())) {
                out.println(message);
                out.flush();
            } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    public User logIn(String login, String password){
        return new User(login, password);
    }
    public User register(String login, String password, String repeat, String name, int age, String sex) throws NotEqualPasswordException {
        if(!password.equals(repeat)){
            throw  new NotEqualPasswordException();
        }
            return new User(login, password, name, age, sex);
    }
}
