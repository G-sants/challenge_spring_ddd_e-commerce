    package g.sants.challenge_e_commerce.domain;

    import com.fasterxml.jackson.annotation.JsonBackReference;
    import com.fasterxml.jackson.annotation.JsonIdentityInfo;
    import com.fasterxml.jackson.annotation.JsonIgnore;
    import com.fasterxml.jackson.annotation.ObjectIdGenerators;
    import g.sants.challenge_e_commerce.application.service.methods.KartOperations;
    import jakarta.persistence.*;

    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @Entity
    public class Kart {

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
        private static HashMap<Long, Item> userKart;

        @ManyToOne
        @JoinColumn(name = "user_id")
        @JsonBackReference
        private User user;


        @JsonIgnore
        @OneToMany(mappedBy = "kart", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        private List<Item> items = new ArrayList<>();

        public Kart() {
            this.id = id;
            this.totalPrice = 0.0;
            this.totalPriceDiscount = 0.0;
            this.totalDiscount = 0.0;
            this.status = KartOperations.status();
            this.date = KartOperations.dateCreation();
        }

        public double getTotalPrice() {
            return KartOperations.totalPrice(items);
        }

        public double getTotalPriceDiscount() {
            return KartOperations
                    .totalPriceDiscount(KartOperations.totalPrice(items));
        }

        public double getTotalDiscount() {
            return KartOperations.totalDiscount(totalPrice);
        }

        public List<Item> getUserKart(long id) {
            return items;
        }

        public void setTotalPrice() {

            this.totalPrice = KartOperations.totalPrice(items);
        }

        public void setTotalPriceDiscount() {
            this.totalPriceDiscount = KartOperations.totalPriceDiscount(getTotalPrice());
        }

        public void setTotalDiscount() {
            this.totalDiscount = KartOperations.totalDiscount(getTotalPrice());
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
            item.setKart(this);
        }

        public void removeItem(Item item) {
            items.remove(item);
            item.setKart(null);
        }
    }
