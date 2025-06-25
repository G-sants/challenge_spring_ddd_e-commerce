package g.sants.challenge_e_commerce.application.dto;

import g.sants.challenge_e_commerce.application.service.methods.UserCategory;

public record RegisterDTORequest(Long customerID, String name, String lastName,
                                 String email, String password, UserCategory category) {
}
