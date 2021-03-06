package controller;



import model.*;
import privious.utils.IOUtils;
import view.MainFrame;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Observable;
import java.util.Observer;

public class Client extends Observable{
    private final int PORT = 5555;
    private static final int USER_SERVER_SOCKET_PORT = 6789;
    private final String IP = "127.0.0.1";
    private Socket client;
    private User user;
    private InputStream is;
    private OutputStream os;
    private DataInputStream ois;
    private ObjectOutputStream oos;
    private MessageSender messageSender;
    private Observer observer;



    public Client() throws IOException {
        client = new Socket(IP, PORT);
        if(client.isConnected()) {
            System.out.println("Connected to the server!");
        }
        this.is = client.getInputStream();
        this.os = client.getOutputStream();
        this. oos = new ObjectOutputStream(os);
        this. ois = new DataInputStream(is);

    }

    /**
     * Send user info to server and receive answer
     *
     * @return answer:
     *          true - if user were previously registered and now enter correct login and password
     *          false - if user weren't previously registered or now doesn't enter correct login and password
     **/
    public boolean logIn(String login, String password) {

        try  {

            this.user = new User(login, password);
            oos.writeObject(user);
            oos.flush();
            return ois.readBoolean();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

      /**
     * Creates two threads:
     *  1. MessageReceiver() - monitoring for messages from server
     *  2. MessageSender() - wait for users messages, send them to server
     * */
    public void startChat(Observer observer) throws IOException {

        this.observer = observer;
        addObserver(observer);

        messageSender = new MessageSender();
        messageSender.start();

        Thread messageReceiver = new MessageReceiver();
        messageReceiver.start();

    }
    public boolean register(String nickName, String password, String passwordRepeat,String name, String surname, LocalDate birthDate, Gender gender) throws FieldLengthIsToBigException, IncorrectPasswordRepeatException, IOException {
        User user = new User(nickName, password, passwordRepeat,name, surname, birthDate, gender);
        Socket registerClient = new Socket(IP, USER_SERVER_SOCKET_PORT);
//todo empty fields
        try (DataInputStream objectInputStream = new DataInputStream(registerClient.getInputStream());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(registerClient.getOutputStream())){

            objectOutputStream.writeObject(user);
            objectOutputStream.flush();

            return objectInputStream.readBoolean();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean logOut(){
        return false;
    }
    /**
     * Thread is responsible for message sending
     *
     *
     **/
    private class MessageReceiver extends Thread{

        @Override
        public void run() {
            try(BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))) {

                String serverMessage;
//todo when server die
                while (client.isConnected()) {
                        serverMessage = in.readLine();
                    if (serverMessage != null) {
                        setChanged();
                        notifyObservers(serverMessage);
                        System.out.println((char) 27 + "[34m" + serverMessage);
                        if (serverMessage.equalsIgnoreCase("quit")) {
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class MessageSender extends Thread{
       private PrintWriter out;

        public MessageSender() {
            try {
                this.out = new PrintWriter(client.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            /*try(PrintWriter out = new PrintWriter(client.getOutputStream());
                 BufferedReader console = new BufferedReader (new InputStreamReader(System.in))) {
                while (isAlive()) {
                    String writeMessage = console.readLine();
                    out.println(writeMessage);
                    out.flush();
                    if (writeMessage.equalsIgnoreCase("quit")) {
                        break;
                    }
                }
            } catch (Exception ignore) {*//*NOP*//*}*/

            /*while (true){

            }*/
        }

        public void sendMessage(String message){
            out.println(message);
            out.flush();
        }
    }
    public void send(String message){
        messageSender.sendMessage(message);
    }
}
