package Model;

public enum TileType {

    //-- caratteristiche di base dei blocchi:
    // true -> blocca movimento di link, dei nemici e dei proiettili
    // false -> link, nemici e proiettili possono attraversare questi blocchi

    GROUND(false),
    WALL(true),
    DOOR(false);

    private final boolean solid;

    TileType(boolean solid) {

        this.solid = solid;
    }

    public boolean isSolid()  {
        return solid;
    }
}
