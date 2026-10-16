package Model;



import java.awt.Rectangle;

/**
 * Rappresenta l'oggetto speciale collezionabile (la lettera/icona Gmail).
 * Raccoglierlo nell'ultimo dungeon attiva la sequenza di vittoria della partita.
 */
public class GmailEntity extends Entity{

    private double x,y;
    private boolean alive;

    /**
     * Costruisce l'entità speciale Gmail alle coordinate specificate.
     * @param x coordinata X iniziale.
     * @param y coordinata Y iniziale.
     */
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
    public boolean isAlive() { return alive; }
    public void setAlive(boolean alive) { this.alive = alive; }
    public double getY() {return y;}
    public double getX() {return x;}
}
