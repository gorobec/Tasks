package privious.asynchro_chat;


import privious.utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class Server {
    private final int PORT = 5555;
    private ServerSocket server;

    private BlockingDeque<String> messages = new LinkedBlockingDeque<>(1);
    private Set<Connection> clients = new HashSet<>();
    public Server() {
    }
    public void start () throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String send = messages.take();
                        for (Connection client : clients) {
                            client.out.println(send);
                            client.out.flush();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        try {
            server = new ServerSocket(PORT);
        } catch (IOException e) {
            System.err.println("Can't connect to the port");
            e.printStackTrace();
            System.exit(-1);
        }

        while (true){
            System.out.println("Server waiting...");
            Socket client = server.accept();
            Connection connection = new Connection(client);
            connection.start();
            clients.add(connection);
            System.out.println("Client " + client.getInetAddress() + " connect!");
        }



    }




    private class Connection extends Thread{
        private Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private boolean state = true;

        public Connection (Socket socket) {
            this.client = socket;
            try {
                in = new BufferedReader
                        (new InputStreamReader(client.getInputStream()));
                out = new PrintWriter(client.getOutputStream());
            } catch (IOException ignore) {/*NOP*/}
        }
        public void run () {
            try {
                while (state) {
                out.println("Welcome to the \"Hell yeah\" chat!\nWrite your message...");
                out.flush();
                while (true) {
                    String message = in.readLine();
                    if (message.equalsIgnoreCase("quit")) {
                        state = false;
                        break;
                    }
                    messages.put(message);
                }
            }
            } catch (Exception e) {/*NOP*/
            }finally {
                try {
                    messages.put((char) 27 + "[34mClient " + client.getInetAddress() + " leave chatroom");
                } catch (InterruptedException ignore) {
                   /*NOP*/
                }
                close();
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
