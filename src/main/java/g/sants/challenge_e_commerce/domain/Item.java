package g.sants.challenge_e_commerce.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "item")
public class Item {

    public Item() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double price;
    private String itemName;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "kart_id")
    private Kart kart;

    // Constructor with parameters
    public Item(double price, String itemName, int quantity) {
        this.price = price;
        this.itemName = itemName;
        this.quantity = quantity;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getItemName() {
        return itemName;
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

    public Kart getKart() {
        return kart;
    }

    public void setKart(Kart kart) {
        this.kart = kart;
    }
}