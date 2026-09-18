package Model;

import java.util.*;

public class ShopModel {

    private List <ShopEntry> itemsForSale;

    public ShopModel() {
        itemsForSale = new ArrayList<>();
        loadShop();
    }

    private void loadShop() {
        // Le coordinate (X, Y) li posizionano sotto i fuochi del tuo background "shop.png"
        itemsForSale.add(new ShopEntry(new HealthPotionItem(), 30, 468, 250)); // Destra
        itemsForSale.add(new ShopEntry(new BombItem(), 20, 368, 250));         // Centro
        itemsForSale.add(new ShopEntry(new ArrowItem(), 10, 268, 250));          // Sinistra
    }


    public List<ShopEntry> getItemsForSale() {
        return itemsForSale;
    }

    //metodo per la logica di acquisto
    public boolean buyItem(Player player, String itemName) {

        //1. verifichiamo che l'oggetto richiesto sia in vendita
        Optional<ShopEntry> entryOptional = itemsForSale.stream()
                .filter(entry -> entry.getItem().getName().equals(itemName))
                .findFirst();

        //2. verifichiamo che l'oggetto esiste
        if (entryOptional.isPresent()) {
            ShopEntry entry = entryOptional.get();


            //3. controlliamo se il giocatore ha abbastanza rupie per acquistare l'oggetto
            if (player.getRupees() >= entry.getPrice()) {

                //4. proviamo ad aggiungerlo all'inventario, ma controlliamo se è pieno
                boolean added = player.addItem(entry.getItem());
                if (added) {
                    //5. se l'aggiunta ha successo, sottraiamo le rupie al giocatore
                    player.spendRupees(entry.getPrice());
                    return true;
                    //la transazione è avvenuta con successo
                }
            }
        }
        return false;
    }

}
