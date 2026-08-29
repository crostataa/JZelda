package Model;

public class BowItem extends Item{

    public BowItem() {
        super("Arco", "spara frecce a distanza");
    }

    @Override
    public void usa(Player player) {
        System.out.print("freccia scoccata");
        //qui va chiamato un metodo che permette di sparare una freccia (proiettile)
        //dritta per dritta dalle coordinate di link (e anche dalla direzione in cui sta guardando
    }
}
