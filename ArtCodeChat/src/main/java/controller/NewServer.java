package controller;

import model.Message;
import model.User;
import privious.utils.IOUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by gorobec on 20.03.16.
 */
public class NewServer{
    private static final String HISTORY_PATH = "src/main/resources/ServerHistory.json";
    private static final int PORT = 5555;
    private ServerSocket server;
    private List<User> users;
    private List<Message> history;
    ExecutorService executorService;
    private BlockingDeque<Message> messages = new LinkedBlockingDeque<>();
    private Set<Connection> clients = new HashSet<>();

    public NewServer(){
        executorService = Executors.newFixedThreadPool(5);
        try {
            this.server = new ServerSocket(PORT);
        } catch (IOException e) {
            System.err.println("Can't connect to the port");
            e.printStackTrace();
        }
        history = new ArrayList<>();
    }

    public void start () throws IOException {
        new Thread(() -> {
            while (true) {
                try {
                    Message send = messages.take();
                    for (Connection client : clients) {
                        client.out.println(send);
                        client.out.flush();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        while (true){
            System.out.println("NewServer waiting...");
            Socket client = server.accept();
            System.out.println("Client " + client.getInetAddress() + " connect!");
            Connection connection = new Connection(client);
            executorService.submit(connection);
//            connection.start();
            clients.add(connection);
        }



    }
    private class Connection extends Thread{

        private Socket client;
        private User user;
        private BufferedReader in;
        private PrintWriter out;
        ObjectInputStream ois;
        private boolean state = true;

        public Connection (Socket socket) {
            this.client = socket;
            try {
                in = new BufferedReader
                        (new InputStreamReader(client.getInputStream()));
                out = new PrintWriter(client.getOutputStream());
                ois = new ObjectInputStream(client.getInputStream());
            } catch (IOException ignore) {/*NOP*/}
        }
        public void run () {
            try {
//                todo validation
//                    user = (User) ois.readObject();
                    out.println(new Message("Welcome to the \"Artchat\"! Write your message..."));
                    out.flush();
                    while (true) {
                        Message message = new Message(user, in.readLine());
                        messages.put(message);
                    }
            /*} catch (ClassNotFoundException e) {
                e.printStackTrace();*/
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clients.remove(this);
                    messages.put(new Message(String.format("%s leave chatroom", user)));
                    close();
                } catch (InterruptedException ignore) {
                   /*NOP*/
                }
            }
        }

        private void close() {
            try {
                IOUtils.closeIn(client.getInputStream());
                IOUtils.closeOut(client.getOutputStream());
                client.close();
            } catch (IOException e) {
                System.out.println("Socket closed");
            }
        }

    }
}
