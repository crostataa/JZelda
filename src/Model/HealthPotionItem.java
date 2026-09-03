package Model;

public class HealthPotionItem extends Item{



    public HealthPotionItem() {
        super("Pozione Rossa");

    }

    @Override
    public void usa(Player player) {
        player.heal(player.getMaxHp());
        //deve anche essere rimossa dall'inventario*
    }

}
