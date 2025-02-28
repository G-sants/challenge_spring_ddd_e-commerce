package g.sants.challenge_e_commerce.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Table(name= "kart")
@Entity
public class Kart {

   private User user;
   private double totalPrice;
   private double totalPriceDiscount;
   private List<Item> itemsList = new ArrayList<Item>();

   @OneToMany(mappedBy = "item")
   private List<Item> items;

   @OneToOne
   @JoinColumn(name = "user_id")
   User users;

   public Kart() {}

   public Kart(User user) {
      this.user = user;
      this.totalPrice = getTotalPrice();
      this.totalPriceDiscount = totalPrice;
      this.itemsList = itemsList;
   }

   public void addItems(Item item) {
      itemsList.add(item);
   }

   public void removeItems(Item item) {
      itemsList.remove(item);
   }

   public double getTotalPrice() {
      return totalPrice;
   }

   public void setTotalPrice() {
      for (int i = 0; i<itemsList.size(); i++){
         Item item = itemsList.get(i);
         this.totalPrice += item.getPrice();
      }
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
