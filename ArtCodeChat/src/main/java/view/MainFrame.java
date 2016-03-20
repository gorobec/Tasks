package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Vorobiei on 17.03.2016.
 */
public class MainFrame extends JFrame {
    JPanel mainPanel;
    JMenuBar menuBar;
    public MainFrame() {
        super("ArtChat");
        setSize(600, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        menuBar = new MenuBar();
        setJMenuBar(menuBar);
        setLayout(new GridBagLayout());
        setVisible(true);
    }
}
