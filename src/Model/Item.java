package Model;

public abstract class Item {

    private String name;

    public Item (String nome) {
        this.name = nome;

    }

    // -- metodi getter --

    public String getName() {return name;}
    // METODO ASTRATTO:
    // Ogni tipo di oggetto dovrà implementare COSA succede quando lo usi.
    // Passiamo il 'Player' come parametro così l'oggetto può interagire con le sue statistiche.
    public abstract void usa(Player player);

}

