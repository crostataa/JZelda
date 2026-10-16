package Model;

/**
 * Classe astratta che rappresenta un oggetto equipaggiabile o utilizzabile dal giocatore.
 */
public abstract class Item {

    private String name;

    /**
     * Costruisce un oggetto con il nome specificato.
     * @param nome identificatore testuale dell'oggetto.
     */
    public Item (String nome) {
        this.name = nome;

    }

    // -- metodi getter --

    /**
     * Restituisce il nome dell'oggetto.
     * @return il nome dell'oggetto.
     */
    public String getName() {return name;}

    /**
     * Definisce l'effetto dell'oggetto applicato allo stato del giocatore.
     * @param player il giocatore che utilizza l'oggetto.
     */
    public abstract void usa(Player player);

}

