package g.sants.challenge_e_commerce.application.dto;

import g.sants.challenge_e_commerce.domain.Item;
import g.sants.challenge_e_commerce.domain.Kart;

import java.util.List;

public record KartDTOResponse(List<Item> items, Double totalPrice, Double totalPriceDiscount,
                              Double totalDiscount, String status, String date) {
    public KartDTOResponse(Kart kart){
        this(kart.getItems(), kart.getTotalPrice(), kart.getTotalPriceDiscount(),
                kart.getTotalDiscount(), kart.getStatus(), kart.date);
    }
}
