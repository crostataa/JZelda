package View;

import java.awt.image.BufferedImage;
import java.util.*;
import java.awt.*;
import javax.swing.*;

import Model.*;

public class GamePanel extends JPanel implements Observer {


    private GameModel model;


    //link che cammina
    // Percorsi immagini Link (Movimento Base)
    private final String PATH_LINK_DOWN_1 = "/assets/basicLink1.png"; // Guarda in basso
    private final String PATH_LINK_DOWN_2 = "/assets/basicLink2.png"; // Guarda in basso (animazione)
    private final String PATH_LINK_SIDE_1 = "/assets/basicLink3.png"; // Guarda di lato
    private final String PATH_LINK_SIDE_2 = "/assets/basicLink4.png"; // Guarda di lato (animazione)
    private final String PATH_LINK_UP_1   = "/assets/basicLink5.png"; // Guarda in alto
    private final String PATH_LINK_UP_2   = "/assets/basicLink6.png"; // Il nuovo sprite (animazione)


    private final String PATH_ITEMS = "/assets/NES - The Legend of Zelda - Miscellaneous - Items & Weapons.png";

    // Coordinate [x, y, width, height]
    // LINK (Basic Movement, primo frame rivolto verso il basso)
    private final int[] SPRITE_LINK_DOWN = { 1, 11, 16, 16 };

    // NEMICI
    //octorok
    private final String PATH_OCTOROK_1 = "/assets/octorok1.png"; // Guarda in basso / base
    private final String PATH_OCTOROK_2 = "/assets/octorok2.png"; // Animazione
    private final String PATH_OCTOROK_3 = "/assets/octorok3.png"; // Guarda di lato
    private final String PATH_OCTOROK_4 = "/assets/octorok4.png"; // Guarda di lato
    private final String PATH_OCTO_ROCK = "/assets/octorok_proiettile.png"; // Proiettile

    //moblin
    //percorsi immagini Moblin
    private final String PATH_MOBLIN_1 = "/assets/moblin1.png"; // Guarda in basso / base
    private final String PATH_MOBLIN_2 = "/assets/moblin2.png"; // Guarda in alto
    private final String PATH_MOBLIN_3 = "/assets/moblin3.png"; // Guarda di lato
    private final String PATH_MOBLIN_4 = "/assets/moblin4.png"; // Guarda di lato (animazione)

    // Percorsi armi (Frecce)
    private final String PATH_ARROW_H = "/assets/arma_vs_orizzontale.png";
    private final String PATH_ARROW_V = "/assets/arma_vs_verticale.png";

    // OGGETTI

    private final int[] SPRITE_BOMB      = { 136, 11, 16, 16 }; // Da ritoccare in base al foglio items
    private final int[] SPRITE_POTION    = { 170, 11, 16, 16 }; // Pozione rossa


    //nota, forse meglio singleton*
    public GamePanel() {


        this.setFocusable(true); //per far funzionare le i tasti per usare il gioco; insomma per keyListener
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

    }

    public void setModel(GameModel model) {

        this.model = model;

    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String) {
            String message = (String) arg;
            //quando il gioco si aggiorna o si resetta, ridisegniamo tutto
            if (message.equals("GAME_UPDATE") || message.equals("GAME_RESET")) {
                repaint();
            }
        }
    }
    // x debug
    private void drawHitboxes(Graphics2D g) {
        g.setColor(Color.YELLOW);

        // Disegna la hitbox del giocatore
        if (model.getPlayer() != null) {
            java.awt.Rectangle ph = model.getPlayer().getHitbox();
            g.drawRect(ph.x, ph.y, ph.width, ph.height);
        }

        // Disegna le hitbox dei nemici
        g.setColor(Color.RED);
        model.getEnemies().forEach(e -> {
            java.awt.Rectangle eh = e.getHitbox();
            g.drawRect(eh.x, eh.y, eh.width, eh.height);
        });
    }

    // -- metodi paint --

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //se il model non è assegnato, non si disegna nulla
        if (model == null || model.getCurrentState() == Model.GameState.MENU) return;

        Graphics2D g2d = (Graphics2D) g;

        //ordine di disegno per strati
        drawLevel(g2d);
        drawRupees(g2d);
        drawEnemies(g2d);
        drawPlayer(g2d);
        //drawProjectiles(g2d);*

        //x debug again
        drawHitboxes(g2d);

    }

    private void drawLevel(Graphics2D g) {

        BufferedImage bg = SpriteManager.getInstance().getSprite("/assets/level 1.png");

        if (bg != null) {
            // Disegna l'immagine grande per tutto lo schermo
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
        }

        // Non c'è bisogno di disegnare i Tile logici, servono solo al Model per le collisioni!
    }

    private void drawRupees(Graphics2D g) {

        model.getRupees().forEach(rupee -> {
            g.setColor(rupee.getValue() == 1 ? Color.GREEN : Color.BLUE);
            g.fillOval((int) rupee.getX(), (int) rupee.getY(), 16, 24);
        });
    }

    //per ora usiamo octorok1 ma lo dobbiamo modificare per farlo ruotare in futuro
    //idem per moblin
    private void drawEnemies(Graphics2D g) {
        for (Enemy e : model.getEnemies()) {

            if (e.getClass().getSimpleName().equals("RangedEnemy")) {
                BufferedImage octorokImg = SpriteManager.getInstance().getSprite(PATH_OCTOROK_1);

                if (octorokImg != null) {
                    g.drawImage(octorokImg, (int)e.getX(), (int)e.getY(), 32, 32, null);
                } else {
                    g.setColor(Color.MAGENTA);
                    g.fillRect((int)e.getX(), (int)e.getY(), 32, 32);
                }
            }
            // Gestione del Moblin (MeleeEnemy)
            else {
                BufferedImage moblinImg = SpriteManager.getInstance().getSprite(PATH_MOBLIN_1);

                if (moblinImg != null) {
                    g.drawImage(moblinImg, (int)e.getX(), (int)e.getY(), 32, 32, null);
                } else {
                    g.setColor(Color.RED);
                    g.fillRect((int)e.getX(), (int)e.getY(), 32, 32);
                }
            }
        }
    }

    private void drawPlayer(Graphics2D g) {
        if (model.getPlayer() != null) {
            Player p = model.getPlayer();
            BufferedImage linkImg = null;

            // Determina la direzione in cui Link sta guardando
            // Se si muove orizzontalmente
            if (Math.abs(p.getLastDx()) > Math.abs(p.getLastDy())) {
                linkImg = SpriteManager.getInstance().getSprite(PATH_LINK_SIDE_1);

                // NOTA: Se si muove a sinistra (p.getLastDx() < 0), in futuro dovremo
                // specchiare l'immagine orizzontalmente con Graphics2D.
            }
            // Se si muove verticalmente
            else {
                if (p.getLastDy() < 0) {
                    // Guarda in alto
                    linkImg = SpriteManager.getInstance().getSprite(PATH_LINK_UP_1);
                } else {
                    // Guarda in basso (Default)
                    linkImg = SpriteManager.getInstance().getSprite(PATH_LINK_DOWN_1);
                }
            }

            // Fallback di sicurezza se l'immagine non carica
            if (linkImg != null) {
                g.drawImage(linkImg, (int)p.getX(), (int)p.getY(), 32, 32, null);
            } else {
                g.setColor(Color.GREEN);
                g.fillRect((int)p.getX(), (int)p.getY(), 32, 32);
            }
        }
    }

    private void drawProjectiles(Graphics2D g) {
        for (Projectile p : model.getProjectiles()) {

            // Sasso dell'Octorok
            if (!p.isPlayerProjectile()) {
                BufferedImage rockImg = SpriteManager.getInstance().getSprite(PATH_OCTO_ROCK);
                if (rockImg != null) {
                    g.drawImage(rockImg, (int)p.getX(), (int)p.getY(), 16, 16, null);
                }
            }
            // Freccia (Moblin o Link)
            else {
                BufferedImage arrowImg;

                // Se la velocità orizzontale (dx) è diversa da 0, sta viaggiando in orizzontale
                if (Math.abs(p.getDx()) > 0) {
                    arrowImg = SpriteManager.getInstance().getSprite(PATH_ARROW_H);
                }
                // Altrimenti viaggia in verticale
                else {
                    arrowImg = SpriteManager.getInstance().getSprite(PATH_ARROW_V);
                }

                if (arrowImg != null) {
                    // Le frecce sono allungate, 32x32 garantisce che mantengano le proporzioni della hitbox
                    g.drawImage(arrowImg, (int)p.getX(), (int)p.getY(), 32, 32, null);
                } else {
                    g.setColor(Color.YELLOW);
                    g.fillRect((int)p.getX(), (int)p.getY(), 16, 16);
                }
            }
        }
    }


    private void drawBombs(Graphics2D g) {
        // Presuppone che in Items & Weapons la bomba sia a x=136, y=11 circa
        BufferedImage bombImg = SpriteManager.getInstance().getSubSprite(
                PATH_ITEMS, 136, 11, 16, 16);

        for (BombEntity bomb : model.getActiveBombs()) {
            if (!bomb.hasExploded()) {
                g.drawImage(bombImg, (int)bomb.getX(), (int)bomb.getY(), 32, 32, null);
            } else {
                // Qui in futuro disegnerai il frame dell'esplosione da "Enemy Death Animation.png"
                g.setColor(Color.ORANGE);
                g.fillOval((int)bomb.getX() - 16, (int)bomb.getY() - 16, 64, 64);
            }
        }
    }
}
