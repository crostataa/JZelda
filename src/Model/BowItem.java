package Model;

public class BowItem extends Item {

    public BowItem() {
        super("Arco e Frecce", "Sblocca l'arco o aggiunge 10 frecce al tuo inventario.");
    }

    @Override
    public void usa(Player player) {
        if (!player.hasBow()) {
            player.unlockBow();
            System.out.println("Hai trovato l'arco!");
        }
        player.addArrows(10);
        System.out.println("Frecce ricaricate! Totale: " + player.getArrows());
    }
}
