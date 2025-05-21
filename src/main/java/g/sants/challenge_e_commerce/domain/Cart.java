    package g.sants.challenge_e_commerce.domain;

    import com.fasterxml.jackson.annotation.JsonBackReference;
    import com.fasterxml.jackson.annotation.JsonIdentityInfo;
    import com.fasterxml.jackson.annotation.JsonIgnore;
    import com.fasterxml.jackson.annotation.ObjectIdGenerators;
    import g.sants.challenge_e_commerce.application.service.methods.CartOperations;
    import jakarta.persistence.*;

    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @Entity
    public class Cart {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        @Column(nullable = false)
        private double totalPrice;
        @Column(nullable = false)
        private double totalPriceDiscount;
        @Column(nullable = false)
        private double totalDiscount;
        @Column(nullable = false)
        private String status;
        @Column(nullable = false)
        public String date;
        private static HashMap<Long, Item> usercart;

        @ManyToOne
        @JoinColumn(name = "user_id")
        @JsonBackReference
        private User user;


        @JsonIgnore
        @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        private List<Item> items = new ArrayList<>();

        public Cart() {
            this.id = id;
            this.totalPrice = 0.0;
            this.totalPriceDiscount = 0.0;
            this.totalDiscount = 0.0;
            this.status = CartOperations.status();
            this.date = CartOperations.dateCreation();
        }

        public void setId(long id) {
            this.id = id;
        }

        public double getTotalPrice() {
            return CartOperations.totalPrice(items);
        }

        public double getTotalPriceDiscount() {
            return CartOperations
                    .totalPriceDiscount(CartOperations.totalPrice(items));
        }

        public double getTotalDiscount() {
            return CartOperations.totalDiscount(totalPrice);
        }

        public List<Item> getUsercart(long id) {
            return items;
        }

        public void setTotalPrice() {

            this.totalPrice = CartOperations.totalPrice(items);
        }

        public void setTotalPriceDiscount() {
            this.totalPriceDiscount = CartOperations.totalPriceDiscount(getTotalPrice());
        }

        public void setTotalDiscount() {
            this.totalDiscount = CartOperations.totalDiscount(getTotalPrice());
        }

        public long getId() {
            return id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public User getUser () {
            return user;
        }

        public void setUser (User user) {
            this.user = user;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }

        public List<Item> getItems() {
            return items;
        }

        public void addItem(Item item) {
            items.add(item);
        }

        public void removeItem(Item item) {
            items.remove(item);
        }
    }
