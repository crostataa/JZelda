package View;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class VictoryPanel extends JPanel {


    public VictoryPanel() {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        //ordine di disegno per strati

        //base
        drawBackground(g2d);
    }

    public void drawBackground(Graphics2D g) {

        String image = "/assets/victoryPanell.png";

        BufferedImage bg = SpriteManager.getInstance().getSprite(image);
        if (bg != null) {
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

    }
}
