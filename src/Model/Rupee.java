package Model;

/**
 * Rappresenta una rupia raccoglibile rilasciata dai nemici sconfitti.
 * Può avere valore 1 (rupia verde) o 5 (rupia blu).
 */
public class Rupee extends Entity{

    private int value; //1 la rupia verde; 5 la rupia blu

    /**
     * Costruisce una rupia con posizione e valore specificati.
     * @param x coordinata X iniziale.
     * @param y coordinata Y iniziale.
     * @param value valore in valuta di gioco (1 o 5).
     */
    public Rupee(double x, double y, int value) {
        super(x, y, 16, 24, 0); //how ?? *
        this.value = value;
    }

    @Override
    public void update() {

    }

    /**
     * Restituisce il valore economico della rupia.
     * @return il valore della rupia.
     */
    public int getValue() { return value; }
}
