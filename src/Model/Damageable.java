package Model;

/**
 * Interfaccia per tutte le entità che possiedono salute e possono subire danni (es. Player, Enemy).
 */
public interface Damageable {

    /**
     * Applica una quantità specificata di danno all'entità.
     * @param damage punti danno da infliggere.
     */
    void takeDamage(int damage);

    /**
     * Restituisce i punti vita (HP) attuali dell'entità.
     * @return i punti vita correnti.
     */
    int getHp();
}
