package Model;

public abstract class Item {

    private String name;
    private String description;



    public Item (String nome, String descrizione) {
        this.name = name;
        this.description = description;
    }

    // -- metodi getter --

    public String getName() {return name;}
    public String getDescription() {return description;}

    // METODO ASTRATTO:
    // Ogni tipo di oggetto dovrà implementare COSA succede quando lo usi.
    // Passiamo il 'Player' come parametro così l'oggetto può interagire con le sue statistiche.
    public abstract void usa(Player player);

}

