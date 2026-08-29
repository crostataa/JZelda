package Model;

//non tutti gli item vengono dallo shop, altri vengono dai forzieri;
//per questo motivo creiamo la classe ShopEntry per differenizare gli oggetti dello shop
public class ShopEntry {
    private Item item;
    private int price;

    public ShopEntry(Item item, int price) {
        this.item = item;
        this.price = price;
    }

    // -- getter --

    public Item getItem() { return item; }
    public int getPrice() { return price; }

}
