package Model;

public class BombItem extends Item {

    public BombItem() {
        super("Pacchetto Bombe", "Aggiunge 5 bombe al tuo inventario.");
    }

    @Override
    public void usa(Player player) {
        player.addBombs(5);
        System.out.println("Hai trovato delle bombe! Totale: " + player.getBombs());
    }
}