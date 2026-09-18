package Model;

import org.w3c.dom.css.Rect;

import java.awt.Rectangle;

public class GmailEntity {

    private double x,y;
    private boolean alive;

    public GmailEntity(double x, double y){
        this.x = x;
        this.y = y;
        this.alive = true;
    }

    // ----getter e setter -----
    public Rectangle getHitbox() {return new Rectangle((int) x, (int) y, 22, 19);}
    public boolean isAlive() { return alive; }
    public void setAlive(boolean alive) { this.alive = alive; }
    public double getY() {return y;}
    public double getX() {return x;}
}
