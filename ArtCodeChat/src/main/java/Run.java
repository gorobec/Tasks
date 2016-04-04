import view.MainFrame;
import view.login_window.LoginFrame;

import javax.swing.*;

/**
 * Created by Vorobiei on 17.03.2016.
 */
public class Run {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        new LoginFrame();
//        new MainFrame();
    }
}
