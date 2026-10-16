package Model;

/**
 * Oggetto che fornisce una ricarica di 10 frecce per l'arco di Link.
 */
public class ArrowItem extends Item {

    /**
     * Costruisce l'oggetto frecce.
     */
    public ArrowItem() {
        super("10 frecce");
    }

    /**
     * Aggiunge 10 frecce alla scorta del giocatore.
     * @param player il giocatore a cui assegnare le frecce.
     */
    @Override
    public void usa(Player player) {
        player.addArrows(10);
    }
}