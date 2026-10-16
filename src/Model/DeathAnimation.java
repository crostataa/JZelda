package Model;

/**
 * Gestisce l'animazione particellare temporanea di sconfitta/fumo per i nemici eliminati.
 * Scandisce i 4 frame dell'animazione a intervalli di tick regolari prima di segnarsi come conclusa.
 */
public class DeathAnimation {
    private double x, y;
    private int currentFrame;
    private int timer;
    private final int FRAME_DURATION = 6; // Durata di ogni singolo frame in tick
    private boolean finished;

    /**
     * Costruisce una nuova animazione di morte alle coordinate dell'entità sconfitta.
     * @param x coordinata X iniziale.
     * @param y coordinata Y iniziale.
     */
    public DeathAnimation(double x, double y) {
        this.x = x;
        this.y = y;
        this.currentFrame = 0;
        this.timer = 0;
        this.finished = false;
    }

    public void update() {
        timer++;
        if (timer > FRAME_DURATION) {
            timer = 0;
            currentFrame++;
            if (currentFrame >= 4) { // I frame sono 4 (da 0 a 3)
                finished = true;
            }
        }
    }

    public boolean isFinished() { return finished; }
    public int getCurrentFrame() { return currentFrame; }
    public double getX() { return x; }
    public double getY() { return y; }
}