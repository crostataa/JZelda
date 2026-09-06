package Model;

import java.awt.Rectangle;

public class BombEntity extends Entity {

    private int timer; // 90 frame = 1.5 secondi a 60 FPS
    private boolean exploded;
    private boolean alive;
    private int damage; // Danno elevato

    public BombEntity(double x, double y) {
        // Dimensioni 16x16, velocità 0 perché sta ferma a terra
        super(x, y, 8, 16, 0);
        this.timer = 90;
        this.exploded = false;
        this.alive = true;
        this.damage = 4;



    }

    @Override
    public void update() {
        if (!alive) return;

        timer--;

        // Quando il timer arriva a 0 e non è ancora esplosa -> ESPLODE!
        if (timer <= 0 && !exploded) {
            exploded = true;
            timer = 15; // L'esplosione dura a schermo per 15 frame prima di sparire
        }
        // Quando finisce anche il timer dell'esplosione -> MUORE
        else if (timer <= 0 && exploded) {
            alive = false;
        }
    }



    public boolean isExploding() {
        return exploded;
    }

    public boolean isAlive() {
        return alive;
    }


    // L'area dell'esplosione è molto più grande della bomba stessa (es. 64x64 pixel)
    public Rectangle getExplosionHitbox() {
        return new Rectangle((int)x - 32, (int)y - 32, 64, 64);

    }

    public boolean hasExploded() { return exploded && timer == 15; } //restituisce true solo nel frame esatto in cui avviene l'esplosione

    // -- getter --
    public int getTimer() { return timer; }
    @Override
    public int getHp() {
        return 0;
    }
    public int getDamage() { return damage; }
}
