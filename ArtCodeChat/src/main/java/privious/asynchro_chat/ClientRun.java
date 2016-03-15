package privious.asynchro_chat;

import java.io.IOException;
import java.net.ConnectException;

public class ClientRun {
    public static void main(String[] args) throws IOException {
        /*int count = 0;

            while (true) {
                final int finalCount = count;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            new Client().start();
                        } catch (IOException e) {
                            System.out.println(finalCount + "!!!!!!!!!!");
                        }
                    }
                }).start();
                count++;
            }*/
        new Client().start();
    }

}
