package Model;

import java.awt.*;

public class Tile {

    private double x;
    private double y;
    private int size;
    private TileType tile;

    public Tile(double x, double y, int size, TileType tile) {

        this.x = x;
        this.y = y;
        this.size = size;
        this.tile = tile;

    }

    public boolean isSolid() { return tile.isSolid(); }
    // -- getter --
    public TileType getTile() { return tile; }
    public Rectangle getHitbox() { return new Rectangle((int) x, (int) y, size, size); }

}
