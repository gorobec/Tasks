import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Message;
import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gorobec on 20.03.16.
 */
public class TestGson {
    public static void main(String[] args) throws InterruptedException, IOException {
        List<Message> history = new ArrayList<>();
        User user = new User("Gorobec", "admin");
        for (int i = 1; i < 4; i++) {
            history.add(new Message(user, "Message" + i));
            Thread.sleep(2000);
        }
        String fileName = "src/main/resources/ServerHistory.json";
        Writer writer = new BufferedWriter(new FileWriter(fileName, true));
        String jsonText = new Gson().toJson(history);
        System.out.println(jsonText);
        writer.write(jsonText);
        writer.flush();
        System.out.println("slepping..");
        Thread.sleep(6000);
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String result = "";
        while (reader.ready()){
            result += reader.readLine() + "\n";
        }
        List<Message> newList = new Gson().fromJson(result, new TypeToken<List<Message>>() {
        }.getType());
        for (Message message : newList) {
            System.out.println(message);
        }
    }
}
