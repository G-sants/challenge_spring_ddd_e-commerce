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
    private int quantity;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

    public Item(long id, double price, String itemName) {
        this.id = id;
        this.price = price;
        this.itemName = itemName;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}