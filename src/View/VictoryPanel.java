package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class VictoryPanel extends JPanel {

    private String line1 = "Link non può salvare la principessa,";
    private String line2 = "deve rispondere alle mail degli studenti.";
    private String line3 = "TO BE CONTINUED...";

    private int indiceCarattere1 = 0;
    private int indiceCarattere2 = 0;
    private int indiceCarattere3 = 0;

    private int tickRitardo = 0;

    private Timer timerAnimazione;

    private JButton menuButton;

    public VictoryPanel() {

        this.setBackground(Color.BLACK);
        this.setLayout(null);

        menuButton = new JButton("MENU");
        menuButton.setFont(FontManager.getFont(20f));
        menuButton.setBackground(Color.DARK_GRAY);
        menuButton.setForeground(Color.WHITE);
        menuButton.setFocusPainted(false);
        menuButton.setBounds(20, 20, 120, 40); // (X, Y, larghezza, altezza) -> In alto a sinistra
        menuButton.setVisible(false); // Inizialmente invisibile
        this.add(menuButton);

        //timer da 50ms per effetto typewriter
        timerAnimazione = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (indiceCarattere1 < line1.length()) {
                    indiceCarattere1++;
                } else if (indiceCarattere2 < line2.length()) {
                    indiceCarattere2++;
                } else if (indiceCarattere3 < line3.length()) {
                    indiceCarattere3++;
                } else {
                    if (tickRitardo < 20) {
                        tickRitardo++;
                    } else {
                        if (!menuButton.isVisible()) {
                            menuButton.setVisible(true);
                        }
                    }
                }
                repaint();
            }
        });
    }

    public void startAnimation() {
        indiceCarattere1 = 0;
        indiceCarattere2 = 0;
        tickRitardo = 0;
        timerAnimazione.start();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        BufferedImage bg = SpriteManager.getInstance().getSprite("/assets/finale_base.png");
        if (bg != null) {
            g2d.drawImage(bg, 0,0, getWidth(), getHeight(), null);
        }

        g2d.setFont(FontManager.getFont(20f));
        g2d.setColor(Color.WHITE);

        FontMetrics fm = g2d.getFontMetrics();

        int yLine1 = getHeight() / 2 - 80;
        int yLine2 = yLine1 + 40;
        int yLine3 = getHeight() / 2 + 150;

        String currentText1 = line1.substring(0, indiceCarattere1);
        int x1 = (getWidth() - fm.stringWidth(currentText1)) / 2;
        g2d.drawString(currentText1, x1, yLine1);

        String currentText2 = line2.substring(0, indiceCarattere2);
        int x2 = (getWidth() - fm.stringWidth(currentText2)) / 2;
        g2d.drawString(currentText2, x2, yLine2);

        String currentText3 = line3.substring(0, indiceCarattere3);
        int x3 = (getWidth() - fm.stringWidth(currentText3)) / 2;
        g2d.drawString(currentText3, x3, yLine3);


    }

    //getter
    public JButton getMenuButton() { return menuButton; }

}
