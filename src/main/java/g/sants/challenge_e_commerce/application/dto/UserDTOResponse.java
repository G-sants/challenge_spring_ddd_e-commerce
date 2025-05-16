package g.sants.challenge_e_commerce.application.dto;

import g.sants.challenge_e_commerce.domain.User;

import java.util.List;

public record UserDTOResponse(Long id, Long customerid, String name, String lastName,
                              String email) {

    public UserDTOResponse(User user){
        this(user.getId(),user.getCustomerID(),user.getName(),
                user.getLastName(),user.getEmail());
    }

    public String getName() {
        return name;
    }
}
