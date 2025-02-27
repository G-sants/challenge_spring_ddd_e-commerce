package g.sants.challenge_e_commerce.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Kart {

   private User user;
   private double totalPrice;
   private double totalPriceDiscount;
   private List<Item> items = new ArrayList<Item>();

   @OneToOne
   

   public Kart(User user) {
      this.user = user;
      this.totalPrice = totalPrice;
      this.totalPriceDiscount = totalPrice;
      this.items = items;
   }

   public void addItems(Item item) {
      items.add(item);
   }

   public void removeItems(Item item) {
      items.remove(item);
   }

   public double getTotalPrice() {
      return totalPrice;
   }

   public void setTotalPrice(double totalPrice) {
      this.totalPrice = totalPrice;
   }

   public double getTotalPriceDiscount() {
      return totalPriceDiscount;
   }

   public void setTotalPriceDiscount(double totalPrice) {
      if (this.totalPrice >=500 & this.totalPrice <1000){
         this.totalPriceDiscount = this.totalPrice -(this.totalPrice *0.1);
      }else if (this.totalPrice >=1000 & this.totalPrice <5000) {
         this.totalPriceDiscount = this.totalPrice -(this.totalPrice *0.15);
      }else if (this.totalPrice >=5000){
         this.totalPriceDiscount = this.totalPrice - (this.totalPrice *0.2);
      } else {
         this.totalPriceDiscount = this.totalPrice;
      }
   }
}
