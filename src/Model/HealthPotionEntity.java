package Model;

import java.awt.*;

public class HealthPotionEntity {

    private double x, y;
    private boolean alive;

    public HealthPotionEntity(double x, double y) {
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

    public Rectangle getHitbox() {
        return new Rectangle((int) x, (int) y, 8, 16);
    }
}
