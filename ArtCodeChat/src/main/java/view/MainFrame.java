package view;

import controller.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Vorobiei on 17.03.2016.
 */
public class MainFrame extends JFrame implements Observer{
    JMenuBar menuBar;
    private static final long serialVersionId = 1L;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 350;
    private MainPanel mainPanel;
    private Client client;

    public MainFrame(Client client) {
        super("ArtChat");
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        menuBar = new MenuBar();
        setJMenuBar(menuBar);
        this.mainPanel = new MainPanel();
        add(mainPanel);
        setVisible(true);
            this.client = client;

        try {
            client.startChat(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainPanel.getSendButton().addActionListener(e -> {
            sendMessage();
        });

        mainPanel.getSendMessageArea().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if ((e.getKeyCode() == KeyEvent.VK_ENTER)
                        && ((e.getModifiers() & KeyEvent.SHIFT_MASK) != 0)) {
                    mainPanel.getSendMessageArea().append("\n");
                    return;
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    sendMessage();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    private void sendMessage() {
        client.send(mainPanel.getSendMessageArea().getText().trim());
        mainPanel.getSendMessageArea().setText("");
    }

    public String writeMessage(){
        return mainPanel.getSendMessageArea().getText();
    }

    @Override
    public void update(Observable o, Object arg) {
        mainPanel.getReceiveMessages().append(arg + "\n");
    }
}
