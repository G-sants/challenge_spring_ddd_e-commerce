package g.sants.challenge_e_commerce.domain;

import g.sants.challenge_e_commerce.application.schemas.KartSchema;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.HashMap;

public class Kart {

    private User user;

    private double totalPrice;
    private double totalPriceDiscount;
    private double totalDiscount;

    private HashMap<Long,Item> userKart = new HashMap<>();

    public Kart(double totalPrice, double totalPriceDiscount){
        this.totalPrice = totalPrice;
        this.totalPriceDiscount = KartSchema.totalPriceDiscount(totalPrice);
        this.totalDiscount = KartSchema.totalDiscount(totalPrice);
    }

    public double totalPrice(long id) {
        if(userKart.get(id) != null) {
            for (int i = 0; i < userKart.size(); i++) {
                totalPrice += userKart.get(i).getPrice()*userKart.get(i).getQuantity();
            }
        }
        return totalPrice;
    }

    public double getTotalPriceDiscount(long id) {
        totalPrice = totalPrice(id);
        totalPriceDiscount = KartSchema.totalPriceDiscount(totalPrice);
        return totalPriceDiscount;
    }

}
