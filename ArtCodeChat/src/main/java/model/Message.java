package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by gorobec on 20.03.16.
 */
public class Message {
    private LocalDate date;
    private LocalTime time;
    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");;
    private User user;
    private String text;

    public Message(User user, String text){
        this.date = LocalDate.now();
        this.time = LocalTime.now();
        this.user = user;
        this.text = text;
    }

    public Message(String text){
        this.date = LocalDate.now();
        this.time = LocalTime.now();
        this.text = text;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public User getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return String.format("%s %s > %s", time.format(fmt),user == null ? "" : user, text);
    }
}
