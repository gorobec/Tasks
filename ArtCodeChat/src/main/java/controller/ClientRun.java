package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientRun {
    public static void main(String[] args) throws IOException {

        Client client = new Client();
        client.start();

        try( BufferedReader console = new BufferedReader
                (new InputStreamReader(System.in))) {
            while (true) {
                client.writeMessage(console.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
