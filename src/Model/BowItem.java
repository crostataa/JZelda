package Model;

/**
 * Oggetto che sblocca l'uso dell'arco e aggiunge 10 frecce al giocatore.
 */
public class BowItem extends Item {

    /**
     * Costruisce l'oggetto arco.
     */
    public BowItem() {
        super("Arco e Frecce");
    }

    /**
     * Sblocca l'arco se non ancora posseduto e aggiunge 10 frecce all'inventario.
     * @param player il giocatore che sblocca l'arma.
     */
    @Override
    public void usa(Player player) {
        if (!player.hasBow()) {
            player.unlockBow();

        }
        player.addArrows(10);
    }
}
