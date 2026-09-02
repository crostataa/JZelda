package Model;

import java.awt.Rectangle;

public class BombEntity extends Entity {

    private int timer = 90; // 90 frame = 1.5 secondi a 60 FPS
    private boolean exploded = false;
    private int damage = 4; // Danno elevato

    public BombEntity(double x, double y) {
        // Dimensioni 16x16, velocità 0 perché sta ferma a terra
        super(x, y, 16, 16, 0);
    }

    @Override
    public void update() {
        if (timer > 0) {
            timer--; // Conto alla rovescia
        } else if (!exploded) {
            explode();
        }
    }

    @Override
    public int getHp() {
        return 0;
    }

    private void explode() {
        exploded = true;
        setAlive(false); // La bomba svanisce dopo l'esplosione
    }

    // L'area dell'esplosione è molto più grande della bomba stessa (es. 64x64 pixel)
    public Rectangle getExplosionHitbox() {
        return new Rectangle((int)x - 24, (int)y - 24, 64, 64);
    }

    public boolean hasExploded() { return exploded; }
    public int getDamage() { return damage; }
}
