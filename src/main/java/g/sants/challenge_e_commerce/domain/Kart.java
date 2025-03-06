    package g.sants.challenge_e_commerce.domain;

    import g.sants.challenge_e_commerce.application.schemas.KartSchema;
    import jakarta.persistence.*;

    import java.util.HashMap;

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
        private HashMap<Long,Item> userKart;

        @ManyToOne
        private User user;

        @OneToMany(mappedBy = "kart")
        private Item item;

        public Kart(){
            this.userKart = new HashMap<>();
            this.id = id;
            this.totalPrice = KartSchema.totalPrice(userKart);
            this.totalPriceDiscount = KartSchema.totalPriceDiscount(totalPrice);
            this.totalDiscount = KartSchema.totalDiscount(totalPrice);
            this.status = KartSchema.status();
            this.date = KartSchema.dateCreation();
        }

        public double getTotalPrice() {
            return KartSchema.totalPrice(userKart);
        }

        public double getTotalPriceDiscount() {
            return KartSchema.totalPriceDiscount(totalPrice);
        }

        public double getTotalDiscount() {
            return KartSchema.totalDiscount(totalPrice);
        }

        public HashMap getUserKart(long id){
            return userKart;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public void setTotalPriceDiscount(double totalPriceDiscount) {
            this.totalPriceDiscount = totalPriceDiscount;
        }

        public void setTotalDiscount(double totalDiscount) {
            this.totalDiscount = totalDiscount;
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
    }
