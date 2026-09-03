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

        //se il giocatore è troppo vicino (es. meno di 100 pixel), fugge via
        if (distance < 100 && distance > 0) {
            this.setDirection(-(diffX / distance), -(diffY-distance));
        } else {
            this.setDirection(0,0); //si ferma per sparare
        }

        //logica di sparo
        if (shootCooldown > 0) {
            shootCooldown--;
        } else if (distance < 250) {
            shoot(model, player);
            shootCooldown = 120; //120 ms = 2 sec
        }
    }

    private void shoot(GameModel model, Player player) {
        double diffX = player.getX() - this.x;
        double diffY = player.getY() - this.y;

        double projDx = 0;
        double projDy = 0;

        // Spara dritto lungo l'asse in cui si trova il giocatore
        if (Math.abs(diffX) > Math.abs(diffY)) {
            projDx = Math.signum(diffX);
        } else {
            projDy = Math.signum(diffY);
        }

        // Passa projDx e projDy. Se il tuo costruttore Projectile usa "int",
        // cambialo in "double" nella classe Projectile!
        Projectile p = new Projectile(
                this.x + 8, this.y + 8, // Centrato
                projDx, projDy,
                3.0, // velocità
                this.damage, false
        );
        model.addProjectile(p);
    }

    @Override
    public void update() {

    }
}
