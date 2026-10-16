package Model;

/**
 * Rappresenta un proiettile a traiettoria rettilinea.
 * Può essere generato dal giocatore (frecce con l'arco) o dai nemici (sassi degli Octorok).
 */
public class Projectile extends Entity {

    private double dx; // positivo -> destra; negativo; sinistra
    private double dy; // positivo -> alto; negativo -> basso

    private int damage;

    private boolean isPlayerProjectile; // true = sparato da link; false = sparato dai nemici

    /**
     * Costruisce un proiettile con posizione, direzione, velocità, danno e fazione di appartenenza.
     * @param x coordinata X iniziale.
     * @param y coordinata Y iniziale.
     * @param dx direzione orizzontale.
     * @param dy direzione verticale.
     * @param speed velocità di volo.
     * @param damage danno inflitto all'impatto.
     * @param isPlayerProjectile true se sparato da Link, false se nemico.
     */
    public Projectile(double x, double y, double dx, double dy, double speed, int damage, boolean isPlayerProjectile) {
        super(x, y, 10, 10, speed);
        this.dx = dx;
        this.dy = dy;
        this.setDirection(dx, dy);
        this.damage = damage;
        this.isPlayerProjectile = isPlayerProjectile;


    }



    @Override
    public void update() {
    //il proiettile viaggia in linea retta
        this.x += dx * speed;
        this.y += dy * speed;
    }

    // -- getter --
    public int getDamage() { return damage; }
    public boolean isPlayerProjectile() { return isPlayerProjectile; }
}
