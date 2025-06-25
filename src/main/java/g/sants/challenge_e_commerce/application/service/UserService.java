package g.sants.challenge_e_commerce.application.service;

import g.sants.challenge_e_commerce.application.dto.UserDTORequest;
import g.sants.challenge_e_commerce.application.dto.UserDTOResponse;
import g.sants.challenge_e_commerce.application.exceptions.errors.UserNotFoundException;
import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import g.sants.challenge_e_commerce.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<UserDTOResponse> getAllUsers() {
        return userRepository.findAll ().stream()
                .map(UserDTOResponse::new)
                .collect(Collectors.toList());
    }

    public UserDTOResponse getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(UserDTOResponse::new).orElseThrow(UserNotFoundException::new);
    }

    public User updateUser(Long id, UserDTORequest userDetails) {
        try{
            User user = userRepository.findById(id)
                    .orElseThrow(UserNotFoundException::new);
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
       userRepository.findById(id)
                    .orElseThrow(UserNotFoundException::new);
       userRepository.deleteById(id);
    }

    public Optional<User> getUserForCart(Long id) {
        return userRepository.findById(id);
    }

}