package view.login_window;

import controller.Client;
import view.ErrorFrame;
import view.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by gorobec on 03.04.16.
 */
public class LoginFrame extends JFrame {
    private static final int WIDTH = 628;
    private static final int HEIGHT =470;
    private LoginPane loginPanel;
    private JPanel bg;
    private GridBagLayout layout;
    private GridBagConstraints gbc;
    private JLabel register;
    private JCheckBox autoLogIn;
    private Client client;
    public LoginFrame(){
        super("ArtChat login menu");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        bg = new ContentPanel();
//        add(bg);
        this.layout = new GridBagLayout();
        this.layout.columnWidths = new int[]{319, 319};
        this.layout.rowHeights = new int[]{400, 70};
        this.gbc = new GridBagConstraints();
        setLayout(layout);
        loginPanel = new LoginPane();
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
//        gbc.fill = GridBagConstraints.CENTER;
        layout.addLayoutComponent(loginPanel, gbc);
        loginPanel.getLogInButton().addActionListener(event ->{
            if(client.logIn((String) loginPanel.getLogin().getSelectedItem(), new String(loginPanel.getPassword().getPassword()))){
                new MainFrame(client);
                this.dispose();
            }
        });

/*//        add loging ComboBox
        String[] names = {"admin", "user", "gorobec"};
        this.login = new JComboBox<>(names);
        login.setEditable(true);
        login.setMinimumSize(new Dimension(150, 35));
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.CENTER;
        layout.addLayoutComponent(login, gbc);

//        add password field
        this.password = new JPasswordField();
        password.setMinimumSize(new Dimension(150, 35));
        gbc.gridy = 1;
        layout.addLayoutComponent(password,gbc);

//        add login button
        this.logInButton = new JButton("Log in");
        logInButton.addActionListener(event ->{
            if(client.logIn((String)login.getSelectedItem(), new String(password.getPassword()))){
                new MainFrame(client);
                this.dispose();
            }
        });
        gbc.gridy = 2;
        layout.addLayoutComponent(logInButton, gbc);
*/
//        add register label
        this.register = new JLabel("Register");
        register.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.LAST_LINE_START;
        gbc.insets = new Insets(30, 0, 0, 200);
        layout.addLayoutComponent(register, gbc);

//        add checkbox
        this.autoLogIn = new JCheckBox("Auto login");
        autoLogIn.setSelected(true);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.LAST_LINE_END;
        gbc.insets = new Insets(30, 200, 0, 0);
        layout.addLayoutComponent(autoLogIn, gbc);

        add(loginPanel);
        add(register);
        add(autoLogIn);
       try{
           client = new Client();
    } catch (IOException e) {
        e.printStackTrace();
        Object[] options = {"Ok", "More..."};
        int code = JOptionPane.showOptionDialog(this,
                "Can't connect to the server",
                "Connection error",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.ERROR_MESSAGE, null, options, options[0]);
        System.out.println(code);
        if(code == 1){
            new ErrorFrame(this, e.getMessage());
        }
    }
        setVisible(true);
    }
}
