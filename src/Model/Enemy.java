package Model;

/**
 * Classe astratta che rappresenta un nemico nel gioco.
 * Estende {@link Entity} e implementa {@link Damageable}, fornendo la gestione dei punti vita,
 * del danno da contatto e l'animazione dei passi.
 */
public abstract class Enemy extends Entity implements Damageable {
    protected int hp;
    protected int damage;

    /**
     * Costruisce un nemico con posizione, dimensioni, velocità, salute e danno di base.
     * @param x coordinata X iniziale.
     * @param y coordinata Y iniziale.
     * @param width larghezza dell'hitbox.
     * @param height altezza dell'hitbox.
     * @param speed velocità di movimento.
     * @param hp punti vita iniziali.
     * @param damage danno inflitto al contatto con Link.
     */
    public Enemy(double x, double y, int width, int height, double speed, int hp, int damage) {
        super(x, y, width, height, speed);
        this.hp = hp;
        this.damage = damage;
    }

    /**
     * Infligge danno al nemico e ne decreta la morte quando gli HP scendono a zero o meno.
     * @param amount punti danno subiti.
     */
    @Override
    public void takeDamage(int amount) {
        this.hp -= amount;
        if (this.hp <= 0) {
            this.setAlive(false);
        }
    }

    // -- getter --
    @Override public int getHp() { return hp; }
    public int getDamage() {return damage; }

    /**
     * Aggiorna l'intelligenza artificiale o il comportamento specifico del nemico (es. inseguimento o sparo).
     * @param model il modello logico del gioco.
     */
    public abstract void updateBehavior(GameModel model);

    /**
     * Aggiorna la posizione fisica e l'alternanza degli sprite di movimento del nemico.
     */
    @Override
    public void update() {
        if (!isAlive()) return;

        this.x += this.dx * this.speed;
        this.y += this.dy * this.speed;

        // Se il nemico si sta muovendo, alterna i frame
        if (this.dx != 0 || this.dy != 0) {
            spriteCounter++;
            if (spriteCounter > 15) { // 15 tick rende i nemici leggermente più goffi di Link
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }
        } else {
            spriteNum = 1; // Torna fermo se non si muove
        }
    }
}
