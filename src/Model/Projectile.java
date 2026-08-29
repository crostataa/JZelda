package Model;

public class Projectile extends Entity {

    private double dx; // positivo -> destra; negativo; sinistra
    private double dy; // positivo -> alto; negativo -> basso

    private int damage;

    private boolean isPlayerProjectile; // true = sparato da link; false = sparato dai nemici

    public Projectile(double x, double y, double dx, double dy, double speed, int damage, boolean isPlayerProjectile) {
        super(x, y, 10, 10, speed);
        this.dx = dx;
        this.dy = dy;
        this.damage = damage;
        this.isPlayerProjectile = isPlayerProjectile;
    }



    @Override
    public int getHp() {
        return 0;
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
