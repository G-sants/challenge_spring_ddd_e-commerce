package g.sants.challenge_e_commerce.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double price;
    private String itemName;

        @ManyToOne
        @JoinColumn(name = "item_id")
        private Item item;

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
