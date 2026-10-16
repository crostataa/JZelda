package Model;

import java.awt.*;

/**
 * Rappresenta una pozione di cura presente sul terreno, raccoglibile dal giocatore.
 */
public class HealthPotionEntity extends Entity{

    private double x, y;
    private boolean alive;

    /**
     * Costruisce un'entità pozione di cura alle coordinate specificate.
     * @param x coordinata X iniziale.
     * @param y coordinata Y iniziale.
     */
    public HealthPotionEntity(double x, double y) {
        super(x, y, 8, 16, 0);
        this.x = x;
        this.y = y;
        this.alive = true;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    @Override
    public void update() {

    }

    public Rectangle getHitbox() {
        return new Rectangle((int) x, (int) y, 8, 16);
    }
}
