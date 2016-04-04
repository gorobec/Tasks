package view;

import javax.swing.*;

/**
 * Created by gorobec on 03.04.16.
 */
public class JMessagesScrollPane extends JScrollPane {
    public JMessagesScrollPane(JTextArea receiveMessages) {
        super(receiveMessages);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    }
}
