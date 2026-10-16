package Model;
import java.awt.Rectangle;

/**
 * Rappresenta un singolo articolo in vendita nello shop del mercante.
 * Associa un {@link Item} a un prezzo in rupie, una posizione sul bancone e uno stato di disponibilità.
 */
public class ShopEntry {
    private Item item;
    private int price;
    private int x, y; // Coordinate fisiche
    private boolean isAvailable; // Indica se è ancora sul bancone

    /**
     * Costruisce un articolo dello shop.
     * @param item l'oggetto in vendita.
     * @param price il costo in rupie.
     * @param x coordinata X sul bancone dello shop.
     * @param y coordinata Y sul bancone dello shop.
     */
    public ShopEntry(Item item, int price, int x, int y) {
        this.item = item;
        this.price = price;
        this.x = x;
        this.y = y;
        this.isAvailable = true;
    }

    public Rectangle getHitbox() {
        return new Rectangle(x, y, 32, 32);
    }

    // -- getter --
    public Item getItem() { return item; }
    public int getPrice() { return price; }
    public int getX() { return x; }
    public int getY() { return y; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { this.isAvailable = available; }
}