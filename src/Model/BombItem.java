package Model;

/**
 * Oggetto che fornisce un rifornimento di 5 bombe per Link.
 */
public class BombItem extends Item {

    /**
     * Costruisce l'oggetto pacchetto bombe.
     */
    public BombItem() {
        super("Pacchetto Bombe");
    }

    /**
     * Aggiunge 5 bombe alla scorta del giocatore.
     * @param player il giocatore a cui assegnare le bombe.
     */
    @Override
    public void usa(Player player) {
        player.addBombs(5);
    }
}