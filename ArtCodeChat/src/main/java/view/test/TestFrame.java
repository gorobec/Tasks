package view.test;

import view.MenuBar;

import javax.swing.*;
import java.awt.*;

/**
 * Created by gorobec on 02.04.16.
 */
public class TestFrame extends JFrame{
    private JSplitPane chatWindow;

    JMenuBar menuBar;
    public TestFrame() {
        super("ArtChat");
        setSize(600, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        menuBar = new MenuBar();
        setJMenuBar(menuBar);
//        setLayout(new GridBagLayout());
        JTextArea sendMessage = new JTextArea();
        sendMessage.setEditable(true);
        sendMessage.setLineWrap(true);
        sendMessage.setWrapStyleWord(true);
        JScrollPane jScrollPane2 = new JScrollPane(sendMessage);
        jScrollPane2.setPreferredSize(new Dimension(100,50));
        jScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        getContentPane().add(jScrollPane2);
        setVisible(true);
    }
}
