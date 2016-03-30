package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Message;
import model.User;
import privious.utils.IOUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by gorobec on 20.03.16.
 */
public class Server {
    private static final String HISTORY_PATH = "src/main/resources/history.txt";
    private static final String REGISTER_USERS_PATH = "src/main/resources/RegisterUsers.json";
    private static final int PORT = 5555;
//    todo connection limit
    private static final int CONNECTION_LIMIT = 3;
    private static final int USER_SERVER_SOCKET_PORT = 6789;
    private final Lock lock = new ReentrantLock();
    private ServerSocket server;
    private ServerSocket userSaverSocket;
    private Set<User> users;
//    todo history
    private List<Message> history;
    private ExecutorService executorService;
    private Set<Connection> clients = Collections.synchronizedSet(new HashSet<>());
    private BlockingDeque<Message> messages = new LinkedBlockingDeque<>();
    private Writer historyWriter;


    public Server(){
        executorService = Executors.newFixedThreadPool(5);
        try {
            this.server = new ServerSocket(PORT, CONNECTION_LIMIT);
            this.userSaverSocket = new ServerSocket(USER_SERVER_SOCKET_PORT);
            this.historyWriter = new FileWriter(HISTORY_PATH, true);
        } catch (IOException e) {
            System.err.println("Can't connect to the port");
            e.printStackTrace();
        }
        history = new ArrayList<>();
    }

    public void start () throws IOException, ExecutionException, InterruptedException {
        new Thread(() -> {
            //noinspection InfiniteLoopStatement
            while (true) {
                    try {
                        Message message = messages.take();
                        for (Connection client : clients) {
                            client.writer.println(message);
                            client.writer.flush();
                        }
                        historyWriter.write(message.toString() + "\n");
                        historyWriter.flush();
                        history.add(message);
                        for (Message message1 : history) {
                            System.out.println(message1);
                        }
                    } catch (InterruptedException | IOException e) {
                        e.printStackTrace();
                    }
            }
        }).start();

        //noinspection InfiniteLoopStatement
        while (true){
            System.out.println("Server waiting for new clients...");
            Socket client = server.accept();
            Connection connection = new Connection(client);
//            todo threadPool
            executorService.submit(connection);
        }



    }
    private boolean checkUser(User user) {
        lock.lock();
        try {
            this.users = new Gson().fromJson(new FileReader(REGISTER_USERS_PATH), new TypeToken<Set<User>>() {
            }.getType());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return users.contains(user);
    }


    private class Connection extends Thread{

        private Socket client;
        private User user;
        private InputStream is;
        private OutputStream os;
        private ObjectInputStream ois;
        private DataOutputStream oos;
        private BufferedReader in;
        private PrintWriter writer;

        public Connection (Socket socket) {
            this.client = socket;
            try {
                this.is = client.getInputStream();
                this.os = client.getOutputStream();
                oos = new DataOutputStream(os);
                ois = new ObjectInputStream(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void run () {

            Thread recorder = new RegistrationThread();
            try {
                while (client.isConnected()) {
                    user = (User) ois.readObject();
                    boolean isRegister = checkUser(user);
//                    todo if logIn twice
                    oos.writeBoolean(isRegister);
                    if (isRegister) break;
                }
                recorder.interrupt();
                if(client.isConnected()) {
                    clients.add(this);
//                    messages.put(new Message(String.format("%s connect to the chatroom", user)));
                    writer = new PrintWriter(client.getOutputStream());
                    writer.println(new Message("Welcome to the \"Artchat\"! Write your message..."));
                    writer.flush();
                    in = new BufferedReader
                            (new InputStreamReader(client.getInputStream()));

                    while (client.isConnected()) {
//                        locked method, wait for client message
                        String receivedText = in.readLine();
//                        if client disconnect while server waits for message
//                        client send null
                        if(receivedText == null) {
                            break;
                        }
                        Message message = new Message(user, receivedText);
                        messages.put(message);
                    }
                }
            } catch (InterruptedException | IOException | ClassNotFoundException e) {
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
                System.out.println("Client socket closed");
            }
        }

    }
//  todo registration on website
    private class RegistrationThread extends Thread{
        private Socket userClient;
        private InputStream is;
        private OutputStream os;
        private DataOutputStream dos;
        private ObjectInputStream ois;

        public RegistrationThread() {
            start();
        }

        @Override
        public void run() {
            try{
                this.userClient = userSaverSocket.accept();
                this.is = userClient.getInputStream();
                this.os = userClient.getOutputStream();
                this.dos = new DataOutputStream(os);
                this.ois = new ObjectInputStream(is);
                //noinspection LoopStatementThatDoesntLoop
                while (userClient.isConnected()) {
                        User user = (User) ois.readObject();
                        boolean isAlreadyRegister = checkUser(user);
//                    todo if logIn twice

                        if(!isAlreadyRegister){
                            dos.writeBoolean(register(user));
                            dos.flush();
                            break;
                        }
                        dos.writeBoolean(false);
                        dos.flush();
                        break;
                }
            } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
            }
        }

        private boolean register(User user) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String result = "";
            try {
                BufferedReader reader = new BufferedReader(new FileReader(REGISTER_USERS_PATH));
                while (reader.ready()){
                    result += reader.readLine() + "\n";
                }
                Set<User> users = gson.fromJson(result, new TypeToken<Set<User>>() {
                }.getType());
                if(!users.contains(user)) {
                    users.add(user);
//                    rewrite file with register users info
//                    todo append to JSON file
                    //noinspection ResultOfMethodCallIgnored
                    new File(REGISTER_USERS_PATH).delete();
                    Writer writer = new BufferedWriter(new FileWriter(REGISTER_USERS_PATH, true));
                    gson.toJson(users, writer);
                    writer.flush();
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }
}
