package Model;

/**
 * Enumerazione delle tipologie di caselle (Tile) della mappa di gioco.
 * Ciascun tipo definisce se il blocco è solido (ostacolo per movimento e proiettili) o calpestabile.
 */
public enum TileType {

    //-- caratteristiche di base dei blocchi:
    // true -> blocca movimento di link, dei nemici e dei proiettili
    // false -> link, nemici e proiettili possono attraversare questi blocchi

    /** Terreno calpestabile (non solido). */
    GROUND(false),
    /** Muro o ostacolo insormontabile (solido). */
    WALL(true),
    /** Porta o varco di passaggio tra livelli (non solido). */
    DOOR(false);

    private final boolean solid;

    TileType(boolean solid) {

        this.solid = solid;
    }

    /**
     * Indica se la casella è solida e ostacola il movimento.
     * @return true se solida, false altrimenti.
     */
    public boolean isSolid()  {
        return solid;
    }
}
