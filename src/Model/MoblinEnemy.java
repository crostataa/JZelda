package Model;

/**
 * Rappresenta il nemico Moblin.
 * Nemico da mischia che pattuglia a passi discreti e insegue Link
 * muovendosi lungo l'asse cardinale prevalente (stile griglia 8-bit).
 */
public class MoblinEnemy extends Enemy{


    private int stepTimer;
    private static final int STEP_DURATION = 40; // Circa 0.6 secondi per passo

    /**
     * Costruisce un nemico Moblin alle coordinate specificate con 2 HP e 1 danno da contatto.
     * @param x coordinata X iniziale.
     * @param y coordinata Y iniziale.
     */
    public MoblinEnemy(double x, double y) {
        // Dimensioni 32x32, velocità 1.5, 2 HP, 1 di danno
        super(x, y, 32, 32, 1.5, 2, 1);
        this.stepTimer = 0;
    }

    /**
     * Calcola la distanza di Manhattan da Link e aggiorna la direzione di marcia
     * gestendo ostacoli, allineamento e durata del passo.
     * @param model il modello logico del gioco.
     */
    @Override
    public void updateBehavior(GameModel model) {
        if (!isAlive()) return;

        Player player = model.getPlayer();
        double diffX = player.getX() - this.x;
        double diffY = player.getY() - this.y;
        // Distanza di Manhattan (somma dei cateti) tipica dei giochi a 8-bit
        double manhattanDistance = Math.abs(diffX) + Math.abs(diffY);

        this.speed = 1.5;

        // Fuori dal raggio visivo: si ferma
        if (manhattanDistance == 0 || manhattanDistance > 350) {
            this.setDirection(0, 0);
            this.stepTimer = 0;
            return;
        }

        // Se il passo attuale è ancora in corso, continua lungo il cateto scelto
        if (stepTimer > 0) {
            // Se si allinea a Link lungo l'asse o sbatte contro un muro, interrompe il passo
            boolean alignedX = this.dx != 0 && Math.abs(diffX) < 5;
            boolean alignedY = this.dy != 0 && Math.abs(diffY) < 5;
            boolean hitWall = model.getCurrentLevelMap().getSolidTiles()
                    .anyMatch(tile -> tile.getHitbox().intersects(this.getPredictedHitbox()));

            if (alignedX || alignedY || hitWall) {
                stepTimer = 0; // Forza il cambio di asse
            } else {
                stepTimer--;
                return;
            }
        }

        // Sceglie il nuovo cateto da percorrere (quello con distanza maggiore)
        if (Math.abs(diffX) > Math.abs(diffY)) {
            this.setDirection(Math.signum(diffX), 0);
        } else {
            this.setDirection(0, Math.signum(diffY));
        }

        // Se la direzione scelta urta subito un muro, prova l'altro cateto
        boolean hitWall = model.getCurrentLevelMap().getSolidTiles()
                .anyMatch(tile -> tile.getHitbox().intersects(this.getPredictedHitbox()));
        if (hitWall) {
            if (this.dx != 0 && Math.abs(diffY) > 5) {
                this.setDirection(0, Math.signum(diffY));
            } else if (this.dy != 0 && Math.abs(diffX) > 5) {
                this.setDirection(Math.signum(diffX), 0);
            }
        }

        stepTimer = STEP_DURATION;
    }
}
