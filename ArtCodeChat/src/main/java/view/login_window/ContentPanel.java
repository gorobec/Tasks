package view.login_window;

import javax.swing.*;
import java.awt.*;

/**
 * Created by gorobec on 03.04.16.
 */
class ContentPanel extends JPanel {
    Image bgimage = null;

    ContentPanel() {
        MediaTracker mt = new MediaTracker(this);
        bgimage = Toolkit.getDefaultToolkit().getImage("src/main/resources/artcode.jpg");
        mt.addImage(bgimage, 0);
        try {
            mt.waitForAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int imwidth = bgimage.getWidth(null);
        int imheight = bgimage.getHeight(null);
        g.drawImage(bgimage, 1, 1, null);
    }
}