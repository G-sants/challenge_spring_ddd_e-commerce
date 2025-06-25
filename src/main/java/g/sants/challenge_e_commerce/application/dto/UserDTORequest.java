package g.sants.challenge_e_commerce.application.dto;

public record UserDTORequest(Long customerID, String name, String lastName,
                             String email, String password) {
}
