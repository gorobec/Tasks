package view.login_window;

import javax.swing.*;
import java.awt.*;

/**
 * Created by gorobec on 03.04.16.
 */
class BgPanel extends JPanel {
    GridBagLayout layout;
    public BgPanel() {
        this.layout = new GridBagLayout();
        setLayout(layout);
        layout.columnWidths = new int[]{1130};
        layout.rowHeights = new int[]{680};
    }

    Image bg = new ImageIcon("src/main/resources/artcode.jpg").getImage();
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
    }
}
