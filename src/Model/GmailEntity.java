package Model;



import java.awt.Rectangle;

public class GmailEntity extends Entity{

    private double x,y;
    private boolean alive;

    public GmailEntity(double x, double y){
        super(x, y, 22, 19, 0);
        this.x = x;
        this.y = y;
        this.alive = true;
    }

    @Override
    public void update() {

    }

    // ----getter e setter -----
    public Rectangle getHitbox() {return new Rectangle((int) x, (int) y, 22, 19);}
    @Override
    public int getHp() {
        return 0;
    }
    public boolean isAlive() { return alive; }
    public void setAlive(boolean alive) { this.alive = alive; }
    public double getY() {return y;}
    public double getX() {return x;}
}
