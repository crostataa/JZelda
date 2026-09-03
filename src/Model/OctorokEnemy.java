package Model;

public class OctorokEnemy extends Enemy{

    private int shootCooldown;

    public OctorokEnemy(double x, double y) {

        //è più fragile e lento del Moblin, ma fa danni a distanza
        super(x, y, 32, 32, 1.0, 1, 1);
        this.shootCooldown = 0;
    }

    @Override
    public void updateBehavior(GameModel model) {
        if (!isAlive()) return;

        Player player = model.getPlayer();
        double diffX = player.getX() - this.x;
        double diffY = player.getY() - this.y;
        double distance = Math.hypot(diffX, diffY);

        // 1. Logica di Fuga o Puntamento
        if (distance < 100 && distance > 0) {
            this.speed = 1.0; // Torna a muoversi per fuggire
            this.setDirection(-(diffX / distance), -(diffY / distance));
        } else {
            this.speed = 0; // Si ferma fisicamente per mirare...
            // ...ma imposta la direzione verso Link per girare lo sprite!
            if (Math.abs(diffX) > Math.abs(diffY)) {
                this.setDirection(Math.signum(diffX), 0);
            } else {
                this.setDirection(0, Math.signum(diffY));
            }
        }

        // 2. Logica di Sparo
        if (shootCooldown > 0) {
            shootCooldown--;
        } else if (distance < 250) {
            shoot(model, player);
            shootCooldown = 120; // Ricarica (circa 2 secondi)
        }
    }

    private void shoot(GameModel model, Player player) {
        double diffX = player.getX() - this.x;
        double diffY = player.getY() - this.y;

        double projDx = 0;
        double projDy = 0;

        // Spara un proiettile dritto, non in diagonale
        if (Math.abs(diffX) > Math.abs(diffY)) {
            projDx = Math.signum(diffX);
        } else {
            projDy = Math.signum(diffY);
        }

        Projectile p = new Projectile(
                this.x + 8, this.y + 8,
                projDx, projDy,
                3.0, // Velocità proiettile
                this.damage,
                false // È un proiettile nemico
        );

        model.addProjectile(p);
    }

}
