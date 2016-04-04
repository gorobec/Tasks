package view;

import javax.swing.*;

/**
 * Created by gorobec on 03.04.16.
 */
public class JMessageArea extends JTextArea {
    public JMessageArea(boolean editable) {
        setEditable(editable);
        setLineWrap(true);
        setWrapStyleWord(true);
    }
}
