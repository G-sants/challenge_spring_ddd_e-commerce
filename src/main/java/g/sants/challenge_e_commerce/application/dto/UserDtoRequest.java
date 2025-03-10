package g.sants.challenge_e_commerce.application.dto;

import g.sants.challenge_e_commerce.domain.User;

public record UserDtoRequest  (Long customerId, String name, String lastName, String email) {
}
