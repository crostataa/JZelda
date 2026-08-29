package Model;

public abstract class Enemy extends Entity {
    protected int hp;
    protected int damage;

    public Enemy(double x, double y, int width, int height, double speed, int hp, int damage) {
        super(x, y, width, height, speed);
        this.hp = hp;
        this.damage = damage;
    }

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

    // Metodo astratto che forza i nemici a definire il proprio comportamento a seconda se individuano il giocatore o meno

    public abstract void updateBehavior(GameModel model);

    @Override
    public void update() {
        // Logica di aggiornamento indipendente dal giocatore (es. timer di stordimento)
    }
}
