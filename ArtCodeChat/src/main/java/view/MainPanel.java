package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by gorobec on 02.04.16.
 */
public class MainPanel extends JPanel {
    private static final long serialVersionId = 1L;
    private JButton sendButton;
    private JSplitPane chatWindow;
    private JScrollPane contactsPane;
    private GridBagLayout layout;
    private GridBagConstraints gbc;
    private JTextArea receiveMessages;
    private JTextArea sendMessage;

    public MainPanel() {
        this.layout = new GridBagLayout();
        this.layout.columnWidths = new int[]{100, 100, 100, 100, 100, 100};
        this.layout.rowHeights = new int[]{70, 70, 120, 20};
        this.gbc = new GridBagConstraints();
//        setSize(WIDTH, HEIGHT);
        setLayout(layout);


//        SplitPane creation
        receiveMessages = new JMessageArea(false);
        sendMessage = new JMessageArea(true);
        this.chatWindow = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true,
                new JMessagesScrollPane(receiveMessages),
                new JScrollPane(sendMessage));
        chatWindow.setResizeWeight(0.75);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        gbc.gridheight = 4;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        layout.setConstraints(chatWindow, gbc);

//        todo ScrollList creation
        String[] names = {"Admin", "User", "Gorobec", "Kotlya", "Posejdon", "User", "Gorobec", "Kotlya", "Posejdon", "User", "Gorobec", "Kotlya", "Posejdon"};
        JList<String> contacts = new JList<>(names);
        contacts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.contactsPane = new JScrollPane(contacts);
        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 3;
        gbc.weightx = 0.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        layout.setConstraints(contacts, gbc);

        //        Button creation
        this.sendButton = new JButton("Send");
        sendButton.setToolTipText("Send message to chatroom");
        gbc.gridx = 5;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);
        layout.setConstraints(sendButton, gbc);

        add(chatWindow);
        add(contacts);
        add(sendButton);
    }

    public JTextArea getReceiveMessages() {
        return receiveMessages;
    }

    public void setReceiveMessages(JTextArea receiveMessages) {
        this.receiveMessages = receiveMessages;
    }

    public JTextArea getSendMessageArea() {
        return sendMessage;
    }

    public void setSendMessageArea(JTextArea sendMessage) {
        this.sendMessage = sendMessage;
    }

    public JButton getSendButton() {
        return sendButton;
    }

    public void setSendButton(JButton sendButton) {
        this.sendButton = sendButton;
    }

    public JScrollPane getContactsPane() {
        return contactsPane;
    }

    public void setContactsPane(JScrollPane contactsPane) {
        this.contactsPane = contactsPane;
    }
}
