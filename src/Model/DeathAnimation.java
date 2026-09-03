package Model;

public class DeathAnimation {
    private double x, y;
    private int currentFrame;
    private int timer;
    private final int FRAME_DURATION = 6; // Durata di ogni singolo frame in tick
    private boolean finished;

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