    package g.sants.challenge_e_commerce.domain;

    import g.sants.challenge_e_commerce.application.schemas.KartSchema;
    import jakarta.persistence.*;

    import java.util.HashMap;

    @Entity
    public class Kart {

        private User user;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        private double totalPrice;
        private double totalPriceDiscount;
        private double totalDiscount;
        private String status;
        public String date;
        private HashMap<Long,Item> userKart;

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
            return totalPrice;
        }

        public double getTotalPriceDiscount() {
            return totalPriceDiscount;
        }

        public double getTotalDiscount() {
            return totalDiscount;
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
