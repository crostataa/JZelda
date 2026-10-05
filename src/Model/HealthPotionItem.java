package Model;

public class HealthPotionItem extends Item{



    public HealthPotionItem() {
        super("Pozione Cura");

    }

    @Override
    public void usa(Player player) {
        player.heal(player.getMaxHp());
    }

}
