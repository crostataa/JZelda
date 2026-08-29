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

        // Teorema di Pitagora per trovare la distanza totale
        double distance = Math.hypot(diffX, diffY);

        // Se è distante, si muove verso il giocatore
        if (distance > 0 && distance < 300) { // 300 è il raggio visivo
            this.setDirection(diffX / distance, diffY / distance);
        } else {
            this.setDirection(0,0); //il nemico è fermo se il giocatore è lontano*
        }
    }


    @Override
    public void update() {

    }
}
