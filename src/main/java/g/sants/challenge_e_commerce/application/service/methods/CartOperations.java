package g.sants.challenge_e_commerce.application.service.methods;

import g.sants.challenge_e_commerce.domain.Item;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CartOperations {

    public static String dateCreation(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formatedDate = now.format(formatter);
        return "Created at "+formatedDate;
    }
    public static String status(){
        return "PENDING";
    }

     public static double totalPrice(List<Item> userKart) {
         double totalPrice = 0.0;
         if (userKart != null) {
             for (int i = 0; i < userKart.size(); i++) {
                 totalPrice += userKart.get(i).getPrice() * userKart.get(i).getQuantity();
             }
         }
         return totalPrice;
     }

    public static double totalPriceDiscount(double totalPrice) {
        double totalPriceDiscount = 0.0;
        if (totalPrice >=500 && totalPrice <1000){
            totalPriceDiscount = totalPrice -(totalPrice *0.1);
        }else if (totalPrice >=1000 && totalPrice <5000) {
            totalPriceDiscount = totalPrice -(totalPrice*0.15);
        }else if (totalPrice >=5000){
            totalPriceDiscount = totalPrice - (totalPrice *0.2);
        } else {
            totalPriceDiscount = totalPrice;
        }
        return totalPriceDiscount;
    }

    public static double totalDiscount(double totalPrice){
        double totalDiscount = 0.0;
        if (totalPrice >=500 && totalPrice <1000){
            totalDiscount = (totalPrice *0.1);
        }else if (totalPrice >=1000 && totalPrice <5000) {
            totalDiscount = (totalPrice*0.15);
        }else if (totalPrice >=5000){
            totalDiscount = (totalPrice *0.2);
        } else {
            totalDiscount = 0.0;
        }
        return totalDiscount;
    }
}
