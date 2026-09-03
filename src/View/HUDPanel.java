package View;

import Model.GameModel;
import Model.Player;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
public class HUDPanel extends JPanel implements Observer {

    private GameModel model;

    public HUDPanel() {
        this.setPreferredSize(new Dimension(768, 192));
        this.setBackground(Color.BLACK);
    }

    public void setModel(GameModel model) {
        this.model = model;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String) {
            String message = (String) arg;
            if (message.equals("GAME_UPDATE") || message.equals("GAME_RESET")) {
                repaint();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // 1. Carica e disegna lo sfondo dell'HUD
        BufferedImage hudBg = SpriteManager.getInstance().getSprite("/assets/HUDNew.png");

        if (hudBg != null) {
            g2d.drawImage(hudBg, 0, 0, getWidth(), getHeight(), null);
        } else {
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }

        // 2. Disegna i dati dinamici se il model e il player esistono
        if (model != null && model.getPlayer() != null) {
            Player player = model.getPlayer();
            drawQuantities(g2d, player);
            drawLife(g2d, player);
        }
    }

    private void drawQuantities(Graphics2D g, Player player) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 18));

        // Quantità Rupie
        g.drawString("X " + player.getRupees(), 80, 42);

        // Quantità Bombe
        g.drawString("X " + player.getBombs(), 80, 82);

        // Quantità Pozioni
        g.drawString("X " + player.getPotions(), 180, 82);
    }

    private void drawLife(Graphics2D g, Player player) {
        g.setColor(Color.RED);
        g.setFont(new Font("Monospaced", Font.BOLD, 16));
        g.drawString("-LIFE-", 580, 30); // Posizione della scritta Life nell'HUD

        int hp = player.getHp();       // Es. 6 HP (3 cuori pieni)
        int maxHp = player.getMaxHp(); // Es. 6 HP max

        int startX = 550; // Coordinata X iniziale dei cuori
        int startY = 45;  // Coordinata Y iniziale dei cuori
        int heartSpacing = 24; // Spazio tra un cuore e l'altro

        // Carica i tre sprite dei cuori direttamente dagli asset
        BufferedImage fullHeart  = SpriteManager.getInstance().getSprite("/assets/cuorePienoHUD.png");
        BufferedImage halfHeart  = SpriteManager.getInstance().getSprite("/assets/cuoreMezzoHUD.png");
        BufferedImage emptyHeart = SpriteManager.getInstance().getSprite("/assets/cuoreVuotoHUD.png");

        // Ogni cuore vale 2 HP
        for (int i = 0; i < maxHp / 2; i++) {
            int xPos = startX + (i % 8) * heartSpacing;
            int yPos = startY + (i / 8) * heartSpacing; // Va a capo se superano gli 8 cuori

            BufferedImage heartToDraw;

            if (hp >= (i * 2) + 2) {
                heartToDraw = fullHeart;
            } else if (hp == (i * 2) + 1) {
                heartToDraw = halfHeart;
            } else {
                heartToDraw = emptyHeart;
            }

            if (heartToDraw != null) {
                g.drawImage(heartToDraw, xPos, yPos, 20, 20, null);
            }
        }
    }
}