package Model;

public class BowItem extends Item {

    public BowItem() {
        super("Arco e Frecce");
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
