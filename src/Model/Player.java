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

    //movimento ereditato dalla classe entity

    //frame di invulnerabilità dopo aver subito un danno
    private int invulnerabilityTimer;

    //variabili per memorizzare dove guarda link in un certo momento
    private double lastDx, lastDy;

    public AudioManager audioManager;


    public Player(double x, double y) {
        super(400, 300, 32, 32, 4.0);
        this.maxHp = 6;
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
        this.hasBow = true; //test, da levare*
        audioManager = AudioManager.getInstance();

    }

    // Metodo per avviare l'animazione
    public void startAttack() {
        if (!isAttacking) {
            isAttacking = true;
            attackTimer = ATTACK_DURATION;
        }
    }

    // sovrascriviamo il metodo di Entity
    @Override
    public void setDirection(double dx, double dy) {
        super.setDirection(dx, dy);

        // Se si sta muovendo, memorizza la direzione
        if (dx != 0 || dy != 0) {
            this.lastDx = dx;
            this.lastDy = dy;
        }
    }

    public void attack() {
        if (!isAttacking) {
            isAttacking = true;
            attackTimer = 15; // L'attacco dura 15 frame (1/4 di secondo a 60 FPS)
            // Qui andrà anche la logica per generare la Hitbox dell'arma e colpire i nemici*
        }
    }

    // --- LOGICA POZIONE ---
    // La pozione si usa istantaneamente, ripristina la vita e si consuma.
    public void usePotion() {
        if (potions > 0 && hp < maxHp) {
            heal(maxHp); // Ripristina tutti i cuori
            potions--;
            audioManager.getInstance().play(/**/);
        }
    }

    @Override
    public void update() {
        if (!isAlive()) return;

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


    // -- gestione salute e danni --
    @Override
    public void takeDamage(int damage) {
        //subisce danni solo se non è temporaneamente vulnerabile
        if (invulnerabilityTimer <= 0) {
            this.hp -= damage;
            if (this.hp <= 0) {
                this.hp = 0;
                this.setAlive(false);
            }
            //altrimenti diventa invulnerabile per circa un secondo
            invulnerabilityTimer = 60;

        }
    }

    public void heal(int amount) {
        this.hp = Math.min(this.hp + amount, this.maxHp);
    }


    // -- gestione rupie --

    public void addRupees(int amount) {
        this.rupees += amount;
    }


    public boolean spendRupees(int amount) {
        if (this.rupees >= amount) {
            this.rupees -= amount;
            return true;
        }
        return false;
    }

    // -- gestione inventario --

    public boolean addItem(Item item) {
        if (inventory.size() < MAX_ITEMS) {
            inventory.add(item);
            return true;
        }
        return false;
    }

    public boolean hasItem(String itemName) {
        return inventory.stream().anyMatch(i -> i.getName().equals(itemName));
    }

    public void useItem(String itemName) {
        Optional<Item> itemToUse = inventory.stream()
                                            .filter(i -> i.getName().equals(itemName))
                                            .findFirst();
        itemToUse.ifPresent(item -> {
            item.usa(this);
            inventory.remove(item); //solo se oggetto consumabile, analizzare oggetti*
        });
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
    public List<Item> getInventory() { return inventory; }
    public double getLastDx() { return lastDx; }
    public double getLastDy() { return lastDy; }
    public double setX(double coordX) { return this.x = coordX;}
    public double setY(double coordY) { return this.y = coordY;}

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
