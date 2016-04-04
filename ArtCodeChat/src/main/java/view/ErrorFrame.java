package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by gorobec on 03.04.16.
 */
public class ErrorFrame extends JFrame {
    public ErrorFrame(JFrame frame, String message) {
        super("Error");
        setMinimumSize(new Dimension(400, 150));
        setResizable(false);
        setLocationRelativeTo(frame);
        JTextArea messageArea = new JTextArea(message);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        add(scrollPane);
        setVisible(true);
    }
}
