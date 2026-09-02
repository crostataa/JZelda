package View;

import java.awt.image.BufferedImage;
import java.util.*;
import java.awt.*;
import javax.swing.*;

import Model.GameModel;
import Model.Player;
import Model.Enemy;
import Model.Projectile;
import Model.Rupee;

public class GamePanel extends JPanel implements Observer {


    private GameModel model;


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
        drawProjectile(g2d);

        //x debug again
        drawHitboxes(g2d);

    }

    private void drawLevel(Graphics2D g) {
        // Esempio: Carica l'immagine dell'intero livello (es. level 8)
        // Più avanti renderemo il nome del file dinamico in base all'ID del livello
        BufferedImage bg = SpriteManager.getInstance().getSprite("src/assets/level 8.png");

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

    private void drawEnemies(Graphics2D g) {
        model.getEnemies().forEach(enemy -> {
            g.setColor(Color.RED);
            g.fillRect((int) enemy.getX(), (int) enemy.getY(), 32, 32);
        });
    }

    private void drawPlayer(Graphics2D g) {
        Player player = model.getPlayer();
        if (!player.isAlive()) return;

        if (player.isInvulnerable()) {
            if (System.currentTimeMillis() % 200 < 100) {
                return;
            }
        }

        g.setColor(Color.GREEN);
        g.fillRect((int) player.getX(), (int) player.getY(), 32, 32);

        // per indicare la direzione in cui guarda
        g.setColor(Color.YELLOW);
        if (player.getLastDx() > 0) g.fillRect((int) player.getX() + 28, (int) player.getY() + 12, 4, 8); // Guarda a destra
        else if (player.getLastDx() < 0) g.fillRect((int) player.getX(), (int) player.getY() + 12, 4, 8); // Sinistra
        else if (player.getLastDy() > 0) g.fillRect((int) player.getX() + 12, (int) player.getY() + 28, 8, 4); // Giù
        else if (player.getLastDy() < 0) g.fillRect((int) player.getX() + 12, (int) player.getY(), 8, 4); // Su

    }

    private void drawProjectile(Graphics2D g) {
        model.getProjectiles().forEach( p-> {
            g.setColor(p.isPlayerProjectile() ? Color.WHITE : Color.ORANGE);
            g.fillOval((int) p.getX(), (int) p.getY(), 10, 10);
        });
    }
}
