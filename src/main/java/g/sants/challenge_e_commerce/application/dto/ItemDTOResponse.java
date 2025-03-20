package g.sants.challenge_e_commerce.application.dto;

import g.sants.challenge_e_commerce.domain.Item;

public record ItemDTOResponse(Double price,String name, Integer quantity) {

    public ItemDTOResponse(Item item){
        this(item.getPrice(),item.getItemName(),item.getQuantity());
    }
}
