package Model;

public class ArrowItem extends Item {

    // Rimuoviamo i parametri per coerenza con BombItem e HealthPotionItem
    public ArrowItem() {
        super("10 frecce");
    }

    @Override
    public void usa(Player player) {
        player.addArrows(10);
    }
}