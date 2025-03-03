package g.sants.challenge_e_commerce.domain;

import g.sants.challenge_e_commerce.application.schemas.KartSchema;

import java.util.HashMap;

public class Kart {

    private double totalPrice;
    private double totalPriceDiscount;

    private HashMap<Long,Item> userKart = new HashMap<>();
    public Kart() {}

    public Kart(double totalPrice, double totalPriceDiscount){
        this.totalPrice = totalPrice;
        this.totalPriceDiscount = KartSchema.totalPriceDiscount(totalPrice);
    }

    public double totalPrice(long id) {
        if(userKart.get(id) != null) {
            for (int i = 0; i < userKart.size(); i++) {
                totalPrice += userKart.get(i).getPrice();
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
