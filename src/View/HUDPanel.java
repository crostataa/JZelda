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
    private final String HUD_SPRITE_FILE = "/NES - The Legend of Zelda - Miscellaneous - HUD & Pause Screen.png";

    // --- COORDINATE DI RITAGLIO (X, Y, Width, Height) ---
    // Da affinare di +/- qualche pixel in base alla risoluzione del tuo file

    // Cuori (solitamente 8x8 pixel nell'originale, li ingrandiremo disegnandoli)
    private final int[] HEART_FULL  = { 625, 117, 8, 8 };
    private final int[] HEART_HALF  = { 616, 117, 8, 8 };
    private final int[] HEART_EMPTY = { 607, 117, 8, 8 };

    // Icone Oggetti (solitamente 8x16 pixel)
    private final int[] ICON_RUPEE  = { 528, 137, 8, 16 };
    private final int[] ICON_BOMB   = { 573, 137, 8, 16 };
    private final int[] ICON_POTION = { 654, 137, 8, 16 }; // Pozione rossa

    private final int[] ICON_SWORD  = { 537, 137, 8, 16 };
    private final int[] ICON_BOW    = { 591, 137, 8, 16 };

    public HUDPanel() {
        this.setPreferredSize(new Dimension(756, 100)); // Altezza ridotta a 100 per renderlo compatto
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

        // Carica lo sfondo dell'HUD
        BufferedImage hudBg = SpriteManager.getInstance().getSprite("/assets/HUDNew.png");

        if (hudBg != null) {
            // Lo disegna partendo da 0,0 grande tutto il pannello
            g2d.drawImage(hudBg, 0, 0, getWidth(), getHeight(), null);
        } else {
            // Fallback nero se l'immagine non viene trovata
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }

        // Qui in futuro disegnerai i cuori, il conteggio delle rupie e la spada
        // usando le coordinate calcolate rispetto a questo pannello (es. y=100)*
    }

    private void drawConsumables(Graphics2D g, Player player) {
        g.setColor(Color.WHITE);

        // Disegna Rupie
        BufferedImage rupeeSprite = SpriteManager.getInstance().getSubSprite(
                HUD_SPRITE_FILE, ICON_RUPEE[0], ICON_RUPEE[1], ICON_RUPEE[2], ICON_RUPEE[3]);
        g.drawImage(rupeeSprite, 50, 20, 16, 32, null); // Ingrandito 2x
        g.drawString("X " + player.getRupees(), 80, 42);

        // Disegna Bombe (Ipotizzando che player.getBombs() esista, altrimenti usa un placeholder)*
        BufferedImage bombSprite = SpriteManager.getInstance().getSubSprite(
                HUD_SPRITE_FILE, ICON_BOMB[0], ICON_BOMB[1], ICON_BOMB[2], ICON_BOMB[3]);
        g.drawImage(bombSprite, 50, 60, 16, 32, null);
        player.getBombs();

        // Disegna Pozioni
        BufferedImage potionSprite = SpriteManager.getInstance().getSubSprite(
                HUD_SPRITE_FILE, ICON_POTION[0], ICON_POTION[1], ICON_POTION[2], ICON_POTION[3]);
        g.drawImage(potionSprite, 150, 60, 16, 32, null);
        player.getPotions();
    }

    private void drawEquipment(Graphics2D g) {
        // Testi dei Tasti
        g.setColor(Color.WHITE);
        g.drawString("X", 335, 30);
        g.drawString("SPACE", 415, 30);

        // Cornici blu (Slot B e Slot A)
        g.setColor(new Color(0, 100, 255));
        g.drawRect(325, 40, 35, 50); // Slot X (Arco)
        g.drawRect(420, 40, 35, 50); // Slot SPACE (Spada)

        // Disegna Arco nello Slot X
        BufferedImage bowSprite = SpriteManager.getInstance().getSubSprite(
                HUD_SPRITE_FILE, ICON_BOW[0], ICON_BOW[1], ICON_BOW[2], ICON_BOW[3]);
        g.drawImage(bowSprite, 334, 48, 16, 32, null);

        // Disegna Spada nello Slot SPACE
        BufferedImage swordSprite = SpriteManager.getInstance().getSubSprite(
                HUD_SPRITE_FILE, ICON_SWORD[0], ICON_SWORD[1], ICON_SWORD[2], ICON_SWORD[3]);
        g.drawImage(swordSprite, 430, 48, 16, 32, null);
    }

    private void drawLife(Graphics2D g, Player player) {
        g.setColor(Color.RED);
        g.drawString("-LIFE-", 600, 30);

        int hp = player.getHp();
        int maxHp = player.getMaxHp();

        int startX = 550;
        int startY = 45;
        int heartSpacing = 24; // Spazio tra un cuore e l'altro

        BufferedImage fullHeart = SpriteManager.getInstance().getSubSprite(
                HUD_SPRITE_FILE, HEART_FULL[0], HEART_FULL[1], HEART_FULL[2], HEART_FULL[3]);
        BufferedImage emptyHeart = SpriteManager.getInstance().getSubSprite(
                HUD_SPRITE_FILE, HEART_EMPTY[0], HEART_EMPTY[1], HEART_EMPTY[2], HEART_EMPTY[3]);

        // nel gioco originale i cuori vanno a capo dopo 8
        for (int i = 0; i < maxHp; i++) {
            int xPos = startX + (i % 8) * heartSpacing;
            int yPos = startY + (i / 8) * heartSpacing; // Va a capo se superano gli 8 cuori

            if (i < hp) {
                g.drawImage(fullHeart, xPos, yPos, 20, 20, null); // 20x20 per renderli ben visibili
            } else {
                g.drawImage(emptyHeart, xPos, yPos, 20, 20, null);
            }
        }
    }
}