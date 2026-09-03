package Model;
import java.awt.Rectangle;

public class ShopEntry {
    private Item item;
    private int price;
    private int x, y; // Coordinate fisiche
    private boolean isAvailable; // Indica se è ancora sul bancone

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