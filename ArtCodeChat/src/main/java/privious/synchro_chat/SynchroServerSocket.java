package privious.synchro_chat;

import privious.utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class SynchroServerSocket {
    public static void main(String[] args) throws IOException {
        BufferedReader in = null;
        PrintWriter out = null;
        BufferedReader console = null;
        ServerSocket server = null;
        try {
            server = new ServerSocket(5666);
        } catch (IOException e) {
            System.err.println("Can't connect to the port");
            e.printStackTrace();
            System.exit(-1);
        }
        System.out.println("NewServer waiting...");
        Socket client = null;
        try {
            client = server.accept();
        } catch (IOException e) {
            System.err.println("Can't accept");
            System.exit(-1);
        }
        System.out.println("Client connect");
        try {
            in = new BufferedReader
                    (new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream());
            console = new BufferedReader
                    (new InputStreamReader(System.in));
        } catch (IOException e) {
            System.err.println("Socket in/out putStream problems");
            System.exit(-1);
        }
        out.println("Welcome to the \"Hell yeah\" chat!\nWrite your message...");
        out.flush();
        String readMessage, writeMessage;
         while(true) {
             readMessage = in.readLine();
             if(readMessage.equalsIgnoreCase("quit")) {
                 System.out.println((char) 27 + "[34mClient leave chatroom");
                 break;
             }
            System.out.println((char)27 + "[34m" + client.getInetAddress() + " > " + readMessage);
            System.out.print((char)27 + "[35mYou > ");
            writeMessage = console.readLine();
            out.println(writeMessage);
            out.flush();
            if (writeMessage.equalsIgnoreCase("quit")) break;

        }
        IOUtils.closeIn(client.getInputStream());
        IOUtils.closeOut(client.getOutputStream());

    }
}
