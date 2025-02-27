package g.sants.challenge_e_commerce.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double price;
    private String itemName;

    public Item(long id, double price, String itemName) {
        this.id = id;
        this.price = price;
        this.itemName = itemName;
    }

    public long getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public String getItemName() {
        return itemName;
    }
}
