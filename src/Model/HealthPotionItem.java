package Model;

/**
 * Oggetto inventario che rappresenta una pozione di cura completa per Link.
 */
public class HealthPotionItem extends Item{



    /**
     * Costruisce l'oggetto pozione di cura.
     */
    public HealthPotionItem() {
        super("Pozione Cura");

    }

    /**
     * Ripristina istantaneamente tutti i cuori del giocatore.
     * @param player il giocatore da curare.
     */
    @Override
    public void usa(Player player) {
        player.heal(player.getMaxHp());
    }

}
