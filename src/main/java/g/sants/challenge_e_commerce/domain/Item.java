package g.sants.challenge_e_commerce.domain;

import g.sants.challenge_e_commerce.application.dto.ItemDTORequest;
import jakarta.persistence.*;

@Entity
@Table(name = "item")
public class Item {

    public Item() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private String itemName;
    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public Item(double price, String itemName, int quantity) {
        this.price = price;
        this.itemName = itemName;
        this.quantity = quantity;
    }

    public Item(ItemDTORequest itemDTORequest) {
        this.price = itemDTORequest.price();
        this.itemName = itemDTORequest.itemName();
        this.quantity = itemDTORequest.quantity();
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

    public Cart getcart() {
        return cart;
    }

    public void setcart(Cart cart) {
        this.cart = cart;
    }
}