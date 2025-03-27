package g.sants.challenge_e_commerce.application.dto;

import g.sants.challenge_e_commerce.domain.Item;

import java.util.List;

public record KartDTORequest(List<ItemDTORequest> items, String status) {
}
