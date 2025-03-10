package g.sants.challenge_e_commerce.application.service;

import g.sants.challenge_e_commerce.application.dto.UserDtoRequest;
import g.sants.challenge_e_commerce.application.dto.UserDtoResponse;
import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import g.sants.challenge_e_commerce.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        List<User> user = userRepository.findAll();
        return user;
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Users not find with ID"));
    }

    public User createUser(UserDtoRequest user){
        User newuser = new User(user);
        return userRepository.save(newuser);
    }

    public User updateUser(Long id, UserDtoResponse userDetails) {
        try{
            User user = userRepository.findById(id)
                    .orElseThrow(()-> new RuntimeException("User not find with id" + id));
            if (user != null) {
                user.setName(userDetails.name());
                user.setLastName(userDetails.lastName());
                user.setCustomerID(userDetails.customerid());
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
                    .orElseThrow(() -> new RuntimeException("User not Found with this id: " + id)));
            userRepository.deleteById(id);
        }catch (Exception e) {
            throw new RuntimeException("Error deleting user" + e.getMessage());
        }
    }
}
