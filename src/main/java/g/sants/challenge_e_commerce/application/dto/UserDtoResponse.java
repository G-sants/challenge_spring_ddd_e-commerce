package g.sants.challenge_e_commerce.application.dto;

import g.sants.challenge_e_commerce.domain.Kart;
import g.sants.challenge_e_commerce.domain.User;

import java.util.List;

public record UserDtoResponse(Long id, Long customerid, String name, String lastName, String email, List kart ) {

    public UserDtoResponse(User user){
        this(user.getId(),user.getCustomerID(),user.getName(),user.getLastName(),user.getEmail(),user.getKart());
    }
}
