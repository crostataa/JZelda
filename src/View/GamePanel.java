package View;

import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.*;
import java.awt.*;
import javax.swing.*;

import Model.*;

public class GamePanel extends JPanel implements Observer {


    private GameModel model;


    //link che cammina
    private final String PATH_LINK_DOWN_1 = "/assets/basicLink1New.png"; // Guarda in basso
    private final String PATH_LINK_DOWN_2 = "/assets/basicLink2New.png"; // Guarda in basso (animazione)
    private final String PATH_LINK_SIDE_1 = "/assets/basicLink3New.png"; // Guarda di lato
    private final String PATH_LINK_SIDE_2 = "/assets/basicLink4New.png"; // Guarda di lato (animazione)
    private final String PATH_LINK_UP_1   = "/assets/basicLink5New.png"; // Guarda in alto
    private final String PATH_LINK_UP_2   = "/assets/basicLink6New.png";

    //link che attacca
    // Percorsi immagini Link (Attacco Spada)
    private final String PATH_ATTACK_UP   = "/assets/spadaLink3New.png";
    private final String PATH_ATTACK_DOWN = "/assets/spadaLink2New.png";
    private final String PATH_ATTACK_SIDE = "/assets/spadaLink1New.png"; // Usato per Destra e Sinistra

    //oggetti
    private final String PATH_BOMB = "/assets/bombaHUDNew.png";
    //bomba negativa x animazione
    private BufferedImage negativeBombSprite = null;
    private final String PATH_ARROW = "/assets/arma_vs_verticaleNew.png";
    private final String PATH_POTION = "/assets/pozioneHUDNew.png";

    //animazione eliminazione
    private final String PATH_DEATH_ANIM = "/assets/Enemy Death Animation.png";

    //octorok
    private final String PATH_OCTOROK_1 = "/assets/octorok1New.png"; // Guarda in basso / base
    private final String PATH_OCTOROK_2 = "/assets/octorok2New.png"; // Animazione
    private final String PATH_OCTOROK_3 = "/assets/octorok3New.png"; // Guarda di lato
    //private final String PATH_OCTOROK_4 = "/assets/octorok4New.png"; // Guarda di lato
    private final String PATH_OCTO_ROCK = "/assets/octorok_proiettileNew.png"; // Proiettile

    //moblin
    private final String PATH_MOBLIN_1 = "/assets/moblin1New.png"; // Guarda in basso / base
    private final String PATH_MOBLIN_2 = "/assets/moblin2New.png"; // Guarda in alto
    private final String PATH_MOBLIN_3 = "/assets/moblin3New.png"; // Guarda di lato
    private final String PATH_MOBLIN_4 = "/assets/moblin4New.png"; // Guarda di lato (animazione)

    // frecce
    private final String PATH_ARROW_H = "/assets/arma_vs_orizzontaleNew.png";
    private final String PATH_ARROW_V = "/assets/arma_vs_verticaleNew.png";

    //rupie
    private final String PATH_RUPEE_1 = "/assets/rupia1.png"; // Rupia gialla/arancio (Valore 1)
    private final String PATH_RUPEE_5 = "/assets/rupia5.png"; // Rupia blu (Valore 5)


    //nota, forse meglio singleton*
    public GamePanel() {


        this.setFocusable(true); //per far funzionare le i tasti per usare il gioco; insomma per keyListener
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

    }

    private BufferedImage createNegativeImage(BufferedImage original) {


        BufferedImage negative = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int y=0; y < original.getHeight(); y++) {
            for (int x=0; x< original.getWidth(); x++) {
                int rgba = original.getRGB(x, y);
                Color color = new Color(rgba, true);
                Color invertedColor = new Color (255- color.getRed(), 255 - color.getGreen(), 255 - color.getBlue(), color.getAlpha());

                negative. setRGB(x, y, invertedColor.getRGB());
            }
        }
        return negative;
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
        drawBombs(g2d);
        drawEnemies(g2d);
        drawDeathAnimations(g2d);
        drawShop(g2d);
        drawPlayer(g2d);
        drawProjectiles(g2d);


        //x debug again
        drawHitboxes(g2d);

    }

    private void drawLevel(Graphics2D g) {
        int currentId = model.getCurrentLevel();
        // Se siamo nello shop carica "shop.png", altrimenti il classico "level X.png"
        String bgPath = (currentId == 10) ? "/assets/shop.png" : "/assets/level " + currentId + ".png";

        BufferedImage bg = SpriteManager.getInstance().getSprite(bgPath);
        if (bg != null) {
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private void drawShop(Graphics2D g) {
        if (model.getCurrentLevel() != 10 || model.getShop() == null) return;

        g.setColor(Color.WHITE);
        g.setFont(FontManager.getFont(24f));

        for (ShopEntry entry : model.getShop().getItemsForSale()) {
            if (!entry.isAvailable()) continue;

            BufferedImage img = null;
            String itemName = entry.getItem().getName();

            // SALVAVITA: Evita il NullPointerException
            if (itemName == null) itemName = "";

            // Controlla i nomi esatti usati nelle tue classi (es. "Pozione Rossa")
            if (itemName.contains("Pozione")) img = SpriteManager.getInstance().getSprite(PATH_POTION);
            else if (itemName.contains("Bombe")) img = SpriteManager.getInstance().getSprite(PATH_BOMB);
            else if (itemName.contains("frecce")) img = SpriteManager.getInstance().getSprite(PATH_ARROW);

            if (img != null) {
                g.drawImage(img, entry.getX(), entry.getY(), 32, 32, null);
            } else {
                g.setColor(Color.WHITE);
                g.fillRect(entry.getX(), entry.getY(), 32, 32);
            }

            g.drawString(entry.getPrice() + " R", entry.getX() + 2, entry.getY() + 50);
        }
    }

    private void drawRupees(Graphics2D g) {
        if (model.getRupees() == null) return;

        for (Rupee r : model.getRupees()) {
            BufferedImage rupeeImg = null;

            // Seleziona l'immagine in base al valore della rupia
            if (r.getValue() == 5) {
                rupeeImg = SpriteManager.getInstance().getSprite(PATH_RUPEE_5);
            } else {
                rupeeImg = SpriteManager.getInstance().getSprite(PATH_RUPEE_1);
            }

            if (rupeeImg != null) {
                // Le rupie sono strette e alte, 16x32 mantiene le proporzioni originali raddoppiate
                g.drawImage(rupeeImg, (int)r.getX(), (int)r.getY(), 16, 32, null);
            } else {
                // Fallback visivo di sicurezza
                g.setColor(r.getValue() == 5 ? Color.BLUE : Color.YELLOW);
                g.fillRect((int)r.getX(), (int)r.getY(), 16, 32);
            }
        }
    }

    private void drawEnemies(Graphics2D g) {
        for (Enemy e : model.getEnemies()) {
            BufferedImage img = null;
            boolean flipHorizontal = false;

            // 1. OCTOROK (Sparo)
            if (e instanceof OctorokEnemy) {
                OctorokEnemy octo = (OctorokEnemy) e;
                boolean shooting = octo.isShooting();

                if (Math.abs(e.getDx()) > Math.abs(e.getDy())) {
                    // Guarda di lato
                    img = SpriteManager.getInstance().getSprite(shooting ? "/assets/octorok3New.png" : PATH_OCTOROK_3);
                    if (e.getDx() < 0) flipHorizontal = true;
                } else if (e.getDy() < 0) {
                    // Guarda in alto
                    img = SpriteManager.getInstance().getSprite(shooting ? PATH_OCTOROK_2 : "/assets/octorok2New.png");
                } else {
                    // Guarda in basso
                    img = SpriteManager.getInstance().getSprite(shooting ? "/assets/octorok1New.png" : PATH_OCTOROK_1);
                }
            }
            // 2. MOBLIN (Camminata)
            else if (e instanceof MoblinEnemy) {
                int frame = e.getSpriteNum();

                if (Math.abs(e.getDx()) > Math.abs(e.getDy())) {
                    // Guarda di lato: Alterna tra la base (3) e l'animazione (4)
                    img = SpriteManager.getInstance().getSprite(frame == 1 ? PATH_MOBLIN_3 : PATH_MOBLIN_4);

                    // Se cammina verso sinistra, specchia lo sprite
                    if (e.getDx() < 0) flipHorizontal = true;

                } else if (e.getDy() < 0) {
                    // Guarda in alto: Usa SEMPRE l'immagine di base (2)...
                    img = SpriteManager.getInstance().getSprite(PATH_MOBLIN_2);

                    // ...ma al frame 2 specchiamo l'immagine per simulare i passi!
                    if (frame == 2) flipHorizontal = true;

                } else {
                    // Guarda in basso: Usa SEMPRE l'immagine di base (1)...
                    img = SpriteManager.getInstance().getSprite(PATH_MOBLIN_1);

                    // ...ma al frame 2 specchiamo l'immagine per simulare i passi!
                    if (frame == 2) flipHorizontal = true;
                }
            }

            // 3. DISEGNO FINALE
            int drawX = (int) e.getX();
            int drawY = (int) e.getY();

            if (img != null) {
                if (flipHorizontal) {
                    g.drawImage(img, drawX + 32, drawY, -32, 32, null);
                } else {
                    g.drawImage(img, drawX, drawY, 32, 32, null);
                }
            } else {
                g.setColor(Color.RED);
                g.fillRect(drawX, drawY, 32, 32);
            }
        }
    }

    private void drawPlayer(Graphics2D g) {
        if (model.getPlayer() == null) return;

        Player p = model.getPlayer();
        BufferedImage linkImg = null;

        int drawX = (int) p.getX();
        int drawY = (int) p.getY();
        boolean flipHorizontal = false;

        // 1. STATO DI ATTACCO
        if (p.isAttacking()) {
            if (Math.abs(p.getLastDx()) > Math.abs(p.getLastDy())) {
                linkImg = SpriteManager.getInstance().getSprite(PATH_ATTACK_SIDE);
                if (p.getLastDx() < 0) {
                    flipHorizontal = true; // Attacca a sinistra
                    if (linkImg != null) {
                        // Offset: Sposta l'origine a sinistra per compensare la lama
                        drawX = (int) p.getX() - (linkImg.getWidth() * 2 - 32);
                    }
                }
            } else {
                if (p.getLastDy() < 0) {
                    linkImg = SpriteManager.getInstance().getSprite(PATH_ATTACK_UP);
                    if (linkImg != null) {
                        // Offset: Sposta l'origine in alto per compensare la lama
                        drawY = (int) p.getY() - (linkImg.getHeight() * 2 - 32);
                    }
                } else {
                    linkImg = SpriteManager.getInstance().getSprite(PATH_ATTACK_DOWN);
                    // Verso il basso non serve offset perché Link è in alto nell'immagine
                }
            }
        }
        // 2. STATO NORMALE (Movimento o Fermo)
        else {
            if (Math.abs(p.getLastDx()) > Math.abs(p.getLastDy())) {
                // Guarda a Destra / Sinistra
                if (p.getSpriteNum() == 1) {
                    linkImg = SpriteManager.getInstance().getSprite(PATH_LINK_SIDE_1);
                } else {
                    linkImg = SpriteManager.getInstance().getSprite(PATH_LINK_SIDE_2);
                }

                if (p.getLastDx() < 0) flipHorizontal = true;
            } else {
                if (p.getLastDy() < 0) {
                    // Guarda in Alto
                    if (p.getSpriteNum() == 1) {
                        linkImg = SpriteManager.getInstance().getSprite(PATH_LINK_UP_1);
                    } else {
                        linkImg = SpriteManager.getInstance().getSprite(PATH_LINK_UP_2);
                    }
                } else {
                    // Guarda in Basso
                    if (p.getSpriteNum() == 1) {
                        linkImg = SpriteManager.getInstance().getSprite(PATH_LINK_DOWN_1);
                    } else {
                        linkImg = SpriteManager.getInstance().getSprite(PATH_LINK_DOWN_2);
                    }
                }
            }
        }

        // 3. DISEGNO A SCHERMO
        if (linkImg != null) {
            // Moltiplichiamo x2 per scalare la pixel art
            int scaledWidth = linkImg.getWidth() * 2;
            int scaledHeight = linkImg.getHeight() * 2;

            if (flipHorizontal) {
                // Ribalta orizzontalmente usando la larghezza negativa
                g.drawImage(linkImg, drawX + scaledWidth, drawY, -scaledWidth, scaledHeight, null);
            } else {
                g.drawImage(linkImg, drawX, drawY, scaledWidth, scaledHeight, null);
            }
        } else {
            // Fallback verde se manca l'immagine
            g.setColor(Color.GREEN);
            g.fillRect((int)p.getX(), (int)p.getY(), 32, 32);
        }
    }

    private void drawProjectiles(Graphics2D g) {
        for (Projectile p : model.getProjectiles()) {

            // 1. Sasso dell'Octorok
            if (!p.isPlayerProjectile()) {
                BufferedImage rockImg = SpriteManager.getInstance().getSprite(PATH_OCTO_ROCK);
                if (rockImg != null) {
                    g.drawImage(rockImg, (int)p.getX(), (int)p.getY(), 16, 16, null);
                }
            }
            // 2. Freccia di Link
            else {
                BufferedImage arrowImg = null;
                boolean flipHorizontal = false;
                boolean flipVertical = false;

                // Controlliamo l'asse dominante per capire se viaggia in orizzontale o verticale
                if (Math.abs(p.getDx()) > Math.abs(p.getDy())) {
                    arrowImg = SpriteManager.getInstance().getSprite(PATH_ARROW_H);

                    // Se viaggia verso sinistra, ribalta l'immagine orizzontalmente
                    if (p.getDx() < 0) flipHorizontal = true;
                } else {
                    arrowImg = SpriteManager.getInstance().getSprite(PATH_ARROW_V);

                    // Nota: se il tuo sprite verticale punta verso l'ALTO,
                    // ribaltiamo l'immagine quando la freccia viaggia verso il BASSO (dy > 0)
                    if (p.getDy() > 0) flipVertical = true;
                }

                if (arrowImg != null) {
                    int drawX = (int) p.getX();
                    int drawY = (int) p.getY();
                    int width = 32;
                    int height = 32;

                    // Disegno ribaltato orizzontalmente (Sinistra)
                    if (flipHorizontal) {
                        g.drawImage(arrowImg, drawX + width, drawY, -width, height, null);
                    }
                    // Disegno ribaltato verticalmente (Basso)
                    else if (flipVertical) {
                        g.drawImage(arrowImg, drawX, drawY + height, width, -height, null);
                    }
                    // Disegno standard (Destra o Alto)
                    else {
                        g.drawImage(arrowImg, drawX, drawY, width, height, null);
                    }
                } else {
                    g.setColor(Color.YELLOW);
                    g.fillRect((int)p.getX(), (int)p.getY(), 16, 16);
                }
            }
        }
    }

    private void drawDeathAnimations(Graphics2D g) {
        BufferedImage deathSheet = SpriteManager.getInstance().getSprite(PATH_DEATH_ANIM);
        if (deathSheet == null) return;

        int totalFrames = 4;
        int frameWidth = deathSheet.getWidth() / totalFrames; // 64 / 4 = 16 pixel a frame
        int frameHeight = deathSheet.getHeight();

        for (DeathAnimation anim : model.getDeathAnimations()) {
            int currentFrame = Math.min(anim.getCurrentFrame(), totalFrames - 1);

            // Usiamo 'currentFrame' come colonna (col).
            // Sarà SpriteManager a moltiplicarlo internamente per la larghezza (col * width).
            BufferedImage sprite = SpriteManager.getInstance().getSubSprite(
                    PATH_DEATH_ANIM, currentFrame, 0, frameWidth, frameHeight
            );

            if (sprite != null) {
                g.drawImage(sprite, (int)anim.getX(), (int)anim.getY(), 32, 32, null);
            }
        }
    }


    private void drawBombs(Graphics2D g) {
        if (model.getActiveBombs() == null) return;

        BufferedImage bombSprite = SpriteManager.getInstance().getSprite("/assets/bombaHUDNew.png");

        // Creiamo la versione in negativo SOLO LA PRIMA VOLTA che ci serve e poi la teniamo in memoria
        if (bombSprite != null && negativeBombSprite == null) {
            negativeBombSprite = createNegativeImage(bombSprite);
        }

        for (BombEntity bomb : model.getActiveBombs()) {
            if (!bomb.isExploding()) {
                if (bombSprite != null) {

                    // CHICCA ARCADE: La velocità del lampeggio (flash) aumenta quando il timer scende sotto i 30!
                    int flashRate = (bomb.getTimer() < 30) ? 4 : 8;

                    // Alterniamo i due sprite basandoci sul timer della bomba
                    if ((bomb.getTimer() / flashRate) % 2 == 0 && negativeBombSprite != null) {
                        // Disegna la bomba in NEGATIVO
                        g.drawImage(negativeBombSprite, (int)bomb.getX() - 8, (int)bomb.getY() - 8, 24, 24, null);
                    } else {
                        // Disegna la bomba NORMALE
                        g.drawImage(bombSprite, (int)bomb.getX() - 8, (int)bomb.getY() - 8, 16, 32, null);
                    }
                } else {
                    g.setColor(Color.BLUE);
                    g.fillOval((int)bomb.getX(), (int)bomb.getY(), 16, 16);
                }
            } else {
                // LA BOMBA ESPLODE
                g.setColor(Color.ORANGE);
                g.fillOval((int)bomb.getX() - 32, (int)bomb.getY() - 32, 64, 64);

                g.setColor(Color.RED);
                g.fillOval((int)bomb.getX() - 16, (int)bomb.getY() - 16, 32, 32);
            }
        }
    }
}
