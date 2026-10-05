package Model;

import View.AudioManager;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Player extends Entity implements Damageable{

    //attributi di gioco
    private int hp;
    private int maxHp;
    private int rupees;

    private boolean isAttacking;
    private int attackTimer;
    private final int ATTACK_DURATION;



    //inventario (max 3 item)
    private List <Item> inventory;
    private static final int MAX_ITEMS = 3;

    private int bombs;
    private int potions;
    private int arrows;
    private boolean hasBow;

    private int invulnerabilityTimer;

    private double lastDx, lastDy;

    public AudioManager audioManager;

    public Player(double x, double y) {
        super(400, 300, 32, 32, 4.0);
        this.maxHp = 10;
        this.hp = this.maxHp;
        this.rupees = 0;
        this.isAttacking = false;
        this.attackTimer = 0;
        this.ATTACK_DURATION = 15;
        this.inventory = new ArrayList<>();
        this.invulnerabilityTimer = 0;
        this.lastDx = 0;
        this.lastDy = 1;
        this.bombs = 1;
        this.potions = 1;
        this.arrows = 10;
        this.hasBow = false; 
        audioManager = AudioManager.getInstance();

    }

    /** Inizia l'animazione di attacco del giocatore. */
    public void startAttack() {
        if (!isAttacking) {
            isAttacking = true;
            attackTimer = ATTACK_DURATION;
        }
    }

    /**
     * Imposta la direzione di movimento.
     * Memorizza anche l'ultima direzione valida per gli attacchi.
     */
    @Override
    public void setDirection(double dx, double dy) {
        super.setDirection(dx, dy);

        if (dx != 0 || dy != 0) {
            this.lastDx = dx;
            this.lastDy = dy;
        }
    }

    /** Usa una pozione curativa se disponibile e necessaria. */
    public void usePotion() {
        if (potions > 0 && hp < maxHp) {
            heal(maxHp); // Ripristina tutti i cuori
            potions--;
        }
    }

    /** Aggiorna logica di movimento, attacchi e frame di invulnerabilità. */
    @Override
    public void update() {
        if (!isAlive()) return;

        // SCALA IL TIMER DI INVULNERABILITÀ OGNI FRAME
        if (invulnerabilityTimer > 0) {
            invulnerabilityTimer--;
        }


        if (isAttacking) {
            attackTimer--;
            if (attackTimer <= 0) {
                isAttacking = false;
            }
        } else {
            // Movimento fisico
            this.x += this.dx * this.speed;
            this.y += this.dy * this.speed;

            // ANIMAZIONE CAMMINATA: Avanza solo se c'è movimento
            if (this.dx != 0 || this.dy != 0) {
                spriteCounter++;
                if (spriteCounter > 12) { // Cambia frame ogni 12 tick (regola per renderlo più o meno veloce)
                    if (spriteNum == 1) {
                        spriteNum = 2;
                    } else {
                        spriteNum = 1;
                    }
                    spriteCounter = 0; // Resetta il contatore
                }
            } else {
                // Se è fermo, torna sempre alla posizione statica (frame 1)
                spriteNum = 1;
            }
        }
    }

    /** Applica danni al giocatore se non è attualmente invulnerabile. */
    @Override
    public void takeDamage(int damage) {
        if (invulnerabilityTimer <= 0) {
            this.hp -= damage;
            if (this.hp <= 0) {
                this.hp = 0;
                this.setAlive(false);
            }
            invulnerabilityTimer = 60;

        }
    }

    /** Ripristina la salute del giocatore fino al massimo consentito. */
    public void heal(int amount) {
        this.hp = Math.min(this.hp + amount, this.maxHp);
    }

    /** Aggiunge un quantitativo di rupie al portafoglio. */
    public void addRupees(int amount) {
        this.rupees += amount;
    }

    /** Tenta di spendere rupie, restituendo true se i fondi sono sufficienti. */
    public boolean spendRupees(int amount) {
        if (this.rupees >= amount) {
            this.rupees -= amount;
            return true;
        }
        return false;
    }

    /** Aggiunge un oggetto all'inventario se non è pieno. */
    public boolean addItem(Item item) {
        if (inventory.size() < MAX_ITEMS) {
            inventory.add(item);

            if (item instanceof BombItem) {
                addBombs(5);
            } else if (item instanceof HealthPotionItem) {
                addPotions(1);
            } else if (item instanceof ArrowItem) {
                addArrows(10);
            }

            return true;
        }
        return false;
    }

    // -- getter e setter --
    @Override
    public Rectangle getHitbox() { return new Rectangle((int)getX() + 6, (int)getY() + 16, 20, 16);}
    public boolean isInvulnerable() {
        return invulnerabilityTimer > 0;
    }
    @Override public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getRupees() { return rupees; }
    public boolean isAttacking() { return isAttacking; }
    public double getLastDx() { return lastDx; }
    public double getLastDy() { return lastDy; }

    public int getBombs() { return bombs; }
    public void addBombs(int amount) { this.bombs += amount; }
    public void consumeBomb() { if (bombs > 0) bombs--; }

    public int getPotions() { return potions; }
    public void addPotions(int amount) { this.potions += amount; }

    public int getArrows() { return arrows; }
    public void addArrows(int amount) { this.arrows += amount; }
    public void consumeArrow() { if (arrows > 0) arrows--; }

    public boolean hasBow() { return hasBow; }
    public void unlockBow() { this.hasBow = true; }


}
