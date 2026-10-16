package Model;

import java.awt.*;

/**
 * Rappresenta un singolo blocco o casella quadrata all'interno della griglia della mappa.
 */
public class Tile {

    private double x;
    private double y;
    private int size;
    private TileType type;

    /**
     * Costruisce un tile specificando posizione, dimensione e tipologia.
     * @param x coordinata X della casella.
     * @param y coordinata Y della casella.
     * @param size dimensione in pixel del lato della casella.
     * @param type tipo di blocco (GROUND, WALL, DOOR).
     */
    public Tile(double x, double y, int size, TileType type) {

        this.x = x;
        this.y = y;
        this.size = size;
        this.type = type;

    }

    /**
     * Indica se il tile è solido (ostacolo per le collisioni).
     * @return true se solido, false altrimenti.
     */
    public boolean isSolid() { return type.isSolid(); }
    // -- getter --
    public TileType getType() { return type; }
    /**
     * Restituisce l'area rettangolare occupata dal tile.
     * @return l'hitbox del tile.
     */
    public Rectangle getHitbox() { return new Rectangle((int) x, (int) y, size, size); }
    public double getX() { return x; }
    public double getY() { return y; }

}
