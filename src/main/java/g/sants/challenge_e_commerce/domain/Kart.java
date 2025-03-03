package g.sants.challenge_e_commerce.domain;

import g.sants.challenge_e_commerce.application.schemas.KartSchema;
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
      this.totalPrice = 0.0;
      this.totalPriceDiscount = 0.0;
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

   public void setTotalPriceDiscount(double totalPrice){
      totalPriceDiscount = KartSchema.totalPriceDiscount(totalPrice);
      this.totalPriceDiscount = totalPriceDiscount;
   }
}
