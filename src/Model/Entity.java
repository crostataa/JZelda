package Model;
import org.w3c.dom.css.Rect;

import java.awt.Rectangle;

public abstract class Entity {
    // Coordinate e dimensioni
    protected double x;
    protected double y;
    protected int width;
    protected int height;

    // Attributi fisici
    protected double speed;
    protected boolean isAlive;

    //tutte le entità devono avere una direzione
    protected double dx, dy;

    public Entity(double x, double y, int width, int height, double speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.isAlive = true;
        this.dx = 0;
        this.dy = 0;
    }



    // Metodo astratto che ogni entità dovrà implementare (es. per muoversi)
    public abstract void update();

    // Utile per gestire le collisioni nel Model usando gli Stream
    public Rectangle getHitbox() {
        return new Rectangle((int)x, (int)y, width, height);
    }

    //prediciamo dove si troverà l'entità dopo il suo spostamento, in modo da non incastrarsi nei muri
    public Rectangle getPredictedHitbox() {
        int nextX = (int) (x + dx * speed);
        int nextY = (int) (y + dy * speed);
        return new Rectangle(nextX, nextY, width, height);

    }

    // tutte le entità possono cambiare direzione
    public void setDirection(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;

    }


    // -- getter e setter --
    public abstract int getHp();
    public double getX() { return x; }
    public double getY() { return y; }
    public boolean isAlive() { return isAlive; }
    public void setAlive(boolean alive) { this.isAlive = alive; }
    public double getDx() { return dx; }
    public double getDy() { return dy; }

    public void takeDamage(int damage) {

    }
}