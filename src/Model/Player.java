package Model;

import View.AudioManager;

import java.util.*;

public class Player extends Entity implements Damageable{

    //attributi di gioco
    private int hp;
    private int maxHp;
    private int rupees;

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
        super(x, y, 32, 32, 4.0);
        this.maxHp = 6;
        this.hp = this.maxHp;
        this.rupees = 0;
        this.inventory = new ArrayList<Item>();
        this.invulnerabilityTimer = 0;
        this.lastDx = 0;
        this.lastDy = 1;
        this.bombs = 1;
        this.potions = 1;
        this.arrows = 10;
        this.hasBow = true; //test, da levare*
        audioManager = AudioManager.getInstance();

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

        //1. aggiorna la posizione
        this.x += dx * speed;
        this.y += dy * speed;

        //2. incrementa il timer di invulnerabilità se è attivo
        if (invulnerabilityTimer > 0 ) {
            invulnerabilityTimer--;
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
    public boolean isInvulnerable() {
        return invulnerabilityTimer > 0;
    }
    @Override public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getRupees() { return rupees; }
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
