package privious.asynchro_chat;




import privious.utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private final int PORT = 5555;
    private final String IP = "127.0.0.1";
    private BufferedReader in;
    private PrintWriter out;
    private BufferedReader console;
    private Socket client;
    private boolean status = true;

    public Client() {
    }
    public void start() throws IOException {
        //try {
            client = new Socket(IP, PORT);
       // } catch (IOException e) {
          //  System.err.println("Can't connect to the port");
          //  System.exit(-1);
      //  }
        System.out.println("Connected to the server!");
        try {
            Thread receiveMessage = new Thread(new Runnable() {
                @Override
                public void run() {

                    while (status) {
                        try {
                            in = new BufferedReader
                                    (new InputStreamReader(client.getInputStream()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        while (true) {

                            String serverMessage = null;
                            try {
                                serverMessage = in.readLine();
                            } catch (IOException e) {
                           /*NOP*/
                            }
                            if (serverMessage.equalsIgnoreCase("quit")) {
                                break;
                            }
                            System.out.println((char) 27 + "[34m" + serverMessage);


                        }
                    }
                }
            });
            receiveMessage.start();

            out = new PrintWriter(client.getOutputStream());
            console = new BufferedReader
                    (new InputStreamReader(System.in));

            while (true) {
                Thread.sleep(100);
                String writeMessage = console.readLine();
                out.println(writeMessage);
                out.flush();
                if (writeMessage.equalsIgnoreCase("quit")) {
                    status = false;
                    receiveMessage.stop();
                    break;
                }
            }
        } catch (Exception ignore) {/*NOP*/
        } finally {
            close();
        }

    }

    private void close() throws IOException {
        try {
            IOUtils.closeIn(client.getInputStream());
            IOUtils.closeOut(client.getOutputStream());
        } catch (IOException e) {
            System.err.println("Socket closed");
        } finally {
            client.close();
        }
    }
}
