package privious.synchro_chat;

import privious.utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SynchroClientSocket {
    public static void main(String[] args) throws IOException {
        BufferedReader in = null;
        PrintWriter out = null;
        BufferedReader console = null;
        Socket client = null;

        try {
            client = new Socket("127.0.0.1", 5666);
        } catch (IOException e) {
            System.err.println("Can't connect to the port");
            System.exit(-1);
        }
        System.out.println("Connected to the server!");


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
        String readMessage, writeMessage;

        System.out.println((char)27 + "[34m" + in.readLine());
        System.out.println((char)27 + "[34m" + in.readLine());
        while(true) {
            System.out.print((char) 27 + "[35mYou > ");
            writeMessage = console.readLine();
            out.println(writeMessage);
            out.flush();
            if (writeMessage.equalsIgnoreCase("quit")) break;
            readMessage = in.readLine();
            if(readMessage.equalsIgnoreCase("quit")) {
                System.out.println((char) 27 + "[34mClient leave chatroom");
                break;
            }
            System.out.println((char) 27 + "[34m" + client.getInetAddress() + " > " + readMessage);
        }
        IOUtils.closeIn(client.getInputStream());
        IOUtils.closeOut(client.getOutputStream());

    }
}
