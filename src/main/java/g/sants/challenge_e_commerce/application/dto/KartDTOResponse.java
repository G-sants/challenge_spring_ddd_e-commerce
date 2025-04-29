package g.sants.challenge_e_commerce.application.dto;

import g.sants.challenge_e_commerce.domain.Item;
import g.sants.challenge_e_commerce.domain.Kart;

import java.util.List;
import java.util.stream.Collectors;

public record KartDTOResponse(List<ItemDTORequest> items, Double totalPrice, Double totalPriceDiscount,
                              Double totalDiscount, String status, String date) {
    public KartDTOResponse(Kart kart){
        this(kart.getItems().stream().map(item -> new ItemDTORequest(item.getItemName(), item.getPrice(),
                        item.getQuantity())).collect(Collectors.toList())
                , kart.getTotalPrice(), kart.getTotalPriceDiscount(),
                kart.getTotalDiscount(), kart.getStatus(), kart.date);
    }
}
