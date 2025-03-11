    package g.sants.challenge_e_commerce.domain;

    import g.sants.challenge_e_commerce.application.schemas.KartSchema;
    import jakarta.persistence.*;

    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;

    @Entity
    public class Kart {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        private double totalPrice;
        private double totalPriceDiscount;
        private double totalDiscount;
        private String status;
        public String date;
        private static HashMap<Long, Item> userKart;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

        @OneToMany(mappedBy = "kart")
        private List<Item> items = new ArrayList<>();

        public Kart() {
            this.userKart = new HashMap<>();
            this.totalPrice = 0.0;
            this.totalPriceDiscount = 0.0;
            this.totalDiscount = 0.0;
            this.status = "";
            this.date = "";
        }

        public double getTotalPrice() {
            return KartSchema.totalPrice(userKart);
        }

        public static double getTotalPriceDiscount() {
            return KartSchema
                    .totalPriceDiscount(KartSchema.totalPrice(userKart));
        }

        public double getTotalDiscount() {
            return KartSchema.totalDiscount(totalPrice);
        }

        public HashMap getUserKart(long id) {
            return userKart;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = KartSchema.totalPrice(userKart);
        }

        public void setTotalPriceDiscount(double totalPriceDiscount) {
            this.totalPriceDiscount = KartSchema.totalPriceDiscount(getTotalPrice());
        }

        public void setTotalDiscount(double totalDiscount) {
            this.totalDiscount = KartSchema.totalDiscount(getTotalPrice());
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
