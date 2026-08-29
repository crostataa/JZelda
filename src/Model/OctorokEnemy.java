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

        //calcoliamo direzione per proiettile
        double diffX = player.getX() - this.x;
        double diffY = player.getY() - this.y;
        double distance = Math.hypot(diffX,diffY);

        if (distance>0) {
            double projDx = diffX / distance;
            double projDy = diffY / distance;

            //il proiettile parte dal centro del nemico
            //la velocità del proiettile è 3
            Projectile p = new Projectile(
                    this.x +16,
                    this.y + 16,
                    (int) projDx,
                    (int) projDy,
                    3,
                    this.damage,
                    false
            );

            model.addProjectile(p);
        }


    }

    @Override
    public void update() {

    }
}
