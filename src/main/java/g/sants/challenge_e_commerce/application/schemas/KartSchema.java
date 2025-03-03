package g.sants.challenge_e_commerce.application.schemas;

import g.sants.challenge_e_commerce.domain.Item;

public class KartSchema {

    public static double totalPriceDiscount(double totalPrice) {
        double totalPriceDiscount = 0.0;
        if (totalPrice >=500 & totalPrice <1000){
            totalPriceDiscount = totalPrice -(totalPrice *0.1);
        }else if (totalPrice >=1000 & Item.getPrice() <5000) {
            totalPriceDiscount = Item.getPrice() -(totalPrice*0.15);
        }else if (totalPrice >=5000){
            totalPriceDiscount = totalPrice - (totalPrice *0.2);
        } else {
            totalPriceDiscount = totalPrice;
        }
        return totalPriceDiscount;
    }
}
