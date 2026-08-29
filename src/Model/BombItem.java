package Model;

public class BombItem extends Item{

    public BombItem() {
        super("Bomba", "piazza un esplosivo che danneggia nemici e ambiente");

    }

    @Override
    public void usa(Player player) {
        System.out.print("bomba piazzata a coordinate: " + player.getX() + ", " + player.getY());

    }
}
