package view.login_window;

import view.MainFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by gorobec on 04.04.16.
 */
public class LoginPane extends JPanel {
    private GridBagLayout layout;
    private GridBagConstraints gbc;
    private JComboBox<String> login;
    private JPasswordField password;
    private JButton logInButton;
    public LoginPane(){
        this.layout = new GridBagLayout();
        this.layout.columnWidths = new int[]{200};
        this.layout.rowHeights = new int[]{40, 40, 25};
        this.gbc = new GridBagConstraints();
        setLayout(layout);
        //        add loging ComboBox
        String[] names = {"admin", "user", "gorobec"};
        this.login = new JComboBox<>(names);
        login.setFont(new Font("Arial", Font.PLAIN, 24));
        login.setEditable(true);
        login.setMinimumSize(new Dimension(350, 35));
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        layout.addLayoutComponent(login, gbc);

//        add password field
        this.password = new JPasswordField();
//        password.setMinimumSize(new Dimension(150, 35));
        password.setFont(new Font("Arial", Font.PLAIN, 24));
        gbc.gridy = 1;
        layout.addLayoutComponent(password,gbc);

//        add login button
        this.logInButton = new JButton("Log in");
        gbc.gridy = 2;
        layout.addLayoutComponent(logInButton, gbc);

        add(login);
        add(password);
        add(logInButton);
    }

    public JButton getLogInButton() {
        return logInButton;
    }

    public JComboBox<String> getLogin() {
        return login;
    }

    public JPasswordField getPassword() {
        return password;
    }
}
