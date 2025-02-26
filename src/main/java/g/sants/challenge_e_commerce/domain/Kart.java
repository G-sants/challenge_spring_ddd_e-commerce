package g.sants.challenge_e_commerce.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Kart {

   private User user;
   private double price;
   private List kart = new ArrayList<Item>();


}
