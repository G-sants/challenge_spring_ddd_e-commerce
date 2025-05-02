package g.sants.challenge_e_commerce.application.service;

import g.sants.challenge_e_commerce.application.dto.UserDTORequest;
import g.sants.challenge_e_commerce.application.dto.UserDTOResponse;
import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import g.sants.challenge_e_commerce.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTOResponse> getAllUsers() {
        List<UserDTOResponse> userList = userRepository.findAll().stream()
                .map(UserDTOResponse::new)
                .collect(Collectors.toList());
        return userList;
    }

    public UserDTOResponse getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(UserDTOResponse::new).orElseThrow(()->
                new RuntimeException("User not found with id"+ id));
    }

    public User updateUser(Long id, UserDTORequest userDetails) {
        try{
            User user = userRepository.findById(id)
                    .orElseThrow(()-> new RuntimeException("User not find with id" + id));
            if (user != null) {
                user.setName(userDetails.name());
                user.setLastName(userDetails.lastName());
                user.setCustomerID(userDetails.customerID());
                user.setEmail(userDetails.email());
                return userRepository.save(user);
            }
        }catch (Exception e){
            throw new RuntimeException("Error updating user" + e.getMessage());
        }
        return null;
    }

    public void deleteUser(Long id) {
        try {
            Optional<User> user = Optional.ofNullable(userRepository.findById(id)
                    .orElseThrow(() ->
                            new RuntimeException("User not Found with this id: " + id)));
            userRepository.deleteById(id);
        }catch (Exception e) {
            throw new RuntimeException("Error deleting user" + e.getMessage());
        }
    }

    public Optional<User> getUserForKart(Long id) {
        return userRepository.findById(id);
    }
}