package Model;

import java.awt.*;

public class Tile {

    private double x;
    private double y;
    private int size;
    private TileType type;

    public Tile(double x, double y, int size, TileType type) {

        this.x = x;
        this.y = y;
        this.size = size;
        this.type = type;

    }

    public boolean isSolid() { return type.isSolid(); }
    // -- getter --
    public TileType getType() { return type; }
    public Rectangle getHitbox() { return new Rectangle((int) x, (int) y, size, size); }

}
