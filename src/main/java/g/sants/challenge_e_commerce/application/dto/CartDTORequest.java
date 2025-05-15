package g.sants.challenge_e_commerce.application.dto;

import java.util.List;

public record CartDTORequest(List<ItemDTORequest> items, String status) {
}
