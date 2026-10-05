package Model;

import View.AudioManager;

public class OctorokEnemy extends Enemy{

    private int shootCooldown;

    public OctorokEnemy(double x, double y) {

        //è più fragile e lento del Moblin, ma fa danni a distanza
        super(x, y, 32, 32, 1.0, 1, 1);
        this.shootCooldown = 0;
    }

    public boolean isShooting() {
        return shootCooldown > 100;
    }

    @Override
    public void updateBehavior(GameModel model) {
        if (!isAlive()) return;

        Player player = model.getPlayer();
        double diffX = player.getX() - this.x;
        double diffY = player.getY() - this.y;
        double distance = Math.hypot(diffX, diffY);

       if (isShooting()) {
           this.speed = 0;
           faceLink(diffX, diffY);
           shootCooldown--;
           return;
       }

       if (shootCooldown > 0) {
           shootCooldown--;
       }

       if (distance < 100 && distance > 0) {
           this.speed = 1.0;
           this.setDirection(-(diffX / distance), -(diffY / distance));
       } else {
           this.speed = 0;
           faceLink(diffX, diffY);
       }

       if (shootCooldown <= 0 && distance < 250) {
           faceLink(diffX, diffY);
           shoot(model);
           shootCooldown = 120;
       }
    }

    private void faceLink(double diffX, double diffY) {
        if (Math.abs(diffX) > Math.abs(diffY)) {
            this.setDirection(Math.signum(diffX), 0);
        } else {
            this.setDirection(0, Math.signum(diffY));
        }
    }

    private void shoot(GameModel model) {
        double projDx = Math.signum(this.getDx());
        double projDy = Math.signum(this.getDy());

        if (projDx == 0 && projDy == 0) {
            projDy = 1;
    }
        Projectile p = new Projectile(
                this.x + 8, this.y + 8,
                projDx, projDy,
                3.0, // velocità proiettile
                this.damage,
                false // è un proiettile nemico
        );
        AudioManager.getInstance().playSE(7);

        model.addProjectile(p);
    }

}
