package Model;

public class BombItem extends Item {

    public BombItem() {
        super("Pacchetto Bombe");
    }

    @Override
    public void usa(Player player) {
        player.addBombs(5);
    }
}