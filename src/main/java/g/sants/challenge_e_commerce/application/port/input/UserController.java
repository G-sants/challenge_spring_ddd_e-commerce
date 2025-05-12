package g.sants.challenge_e_commerce.application.port.input;

import g.sants.challenge_e_commerce.application.dto.UserDTORequest;
import g.sants.challenge_e_commerce.application.dto.UserDTOResponse;
import g.sants.challenge_e_commerce.application.exceptions.errors.NoInfoReceivedException;
import g.sants.challenge_e_commerce.application.exceptions.errors.UserNotFoundException;
import g.sants.challenge_e_commerce.application.service.UserService;
import g.sants.challenge_e_commerce.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDTOResponse> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTOResponse> getUsersById(@PathVariable Long id){
        UserDTOResponse user = userService.getUser(id);
        if (user == null) {
            throw new NoInfoReceivedException();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDTORequest userDetails){
        User updateUser =userService.updateUser(id, userDetails);
        if(updateUser ==null){
            throw new NoInfoReceivedException();
        }
        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
