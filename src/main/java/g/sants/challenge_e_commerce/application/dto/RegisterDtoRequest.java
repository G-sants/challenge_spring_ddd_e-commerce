package g.sants.challenge_e_commerce.application.dto;

import g.sants.challenge_e_commerce.application.schemas.UserCategory;

public record RegisterDtoRequest(Long customerID, String name, String lastName,
                                 String email, String password, UserCategory category) {
}
