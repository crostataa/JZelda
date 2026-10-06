package Model;

public class BowItem extends Item {

    public BowItem() {
        super("Arco e Frecce");
    }

    @Override
    public void usa(Player player) {
        if (!player.hasBow()) {
            player.unlockBow();

        }
        player.addArrows(10);
    }
}
