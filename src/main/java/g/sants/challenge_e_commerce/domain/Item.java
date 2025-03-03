package g.sants.challenge_e_commerce.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "item")
public class Item {

    public Item() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private static double price;
    private String itemName;

        @ManyToOne
        @JoinColumn(name = "item_id")
        private Item item;

    public Item(long item_id, double price, String itemName) {
        this.id = item_id;
        this.price = price;
        this.itemName = itemName;
    }

    public long getId() {
        return id;
    }

    public static double getPrice() {
        return price;
    }

    public String getItemName() {
        return itemName;
    }


    public static void setPrice(double price) {
        Item.price = price;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}