package Model;

public class MoblinEnemy extends Enemy{


    public MoblinEnemy(double x, double y) {

        // Dimensioni 32x32, velocità 1.5, 2 HP, 1 di danno
        // "impostazioni" standard per entrambi i nemici
        super(x, y, 32, 32, 1.5, 2, 1);
    }

    @Override
    public void updateBehavior(GameModel model) {
        if (!isAlive()) return;

        Player player = model.getPlayer();
        double diffX = player.getX() - this.x;
        double diffY = player.getY() - this.y;
        double distance = Math.hypot(diffX, diffY);

        this.speed = 1.5; // Assicuriamoci che abbia la sua velocità

        // Se è nel raggio visivo, lo insegue muovendosi a croce
        if (distance > 0 && distance < 300) {
            if (Math.abs(diffX) > Math.abs(diffY)) {
                this.setDirection(Math.signum(diffX), 0); // Si muove in orizzontale
            } else {
                this.setDirection(0, Math.signum(diffY)); // Si muove in verticale
            }
        } else {
            this.setDirection(0, 0); // Si ferma
        }
    }



}
