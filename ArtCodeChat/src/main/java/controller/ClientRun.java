package controller;

import model.FieldLengthIsToBigException;
import model.Gender;
import model.IncorrectPasswordRepeatException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;

public class ClientRun {
    public static void main(String[] args) throws IOException, FieldLengthIsToBigException, IncorrectPasswordRepeatException {
        /*int count = 0;

        Client client = new Client();
        client.start();

        try( BufferedReader console = new BufferedReader
                (new InputStreamReader(System.in))) {
            while (true) {
                final int finalCount = count;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            new Client().startChat();
                        } catch (IOException e) {
                            System.out.println(finalCount + "!!!!!!!!!!");
                        }
                    }
                }).startChat();
                count++;
            }*/
        Client client = new Client();
        boolean isLogIn = false;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        /*System.out.println("Enter login");
        String nickName = reader.readLine();
        System.out.println("Enter pass");
        String password = reader.readLine();
        System.out.println("Enter  repeat pass");
        String passwordRepeat = reader.readLine();
        System.out.println("Enter name");
        String name = reader.readLine();
        System.out.println("Enter surname");
        String surname = reader.readLine();
        LocalDate birthDate = LocalDate.now();
        Gender gender = Gender.MALE;

        boolean isRegister = client.register(nickName, password, passwordRepeat, name, surname, birthDate, gender);
        System.out.println(isRegister);*/
        while (!isLogIn){
            System.out.println("Enter login");
            String login = reader.readLine();
            System.out.println("Enter pass");
            String pass = reader.readLine();
            isLogIn = client.logIn(login, pass);
        }
        System.out.println("Log in");
        client.startChat();
    }
    }
