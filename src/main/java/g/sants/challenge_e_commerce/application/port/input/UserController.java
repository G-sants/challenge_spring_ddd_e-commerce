package g.sants.challenge_e_commerce.application.port.input;

import g.sants.challenge_e_commerce.application.dto.UserDTORequest;
import g.sants.challenge_e_commerce.application.dto.UserDTOResponse;
import g.sants.challenge_e_commerce.application.exceptions.errors.NoInfoReceivedException;
import g.sants.challenge_e_commerce.application.service.UserService;
import g.sants.challenge_e_commerce.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTOResponse> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<UserDTOResponse> getUsersById(@PathVariable Long user_id){
        UserDTOResponse user = userService.getUser(user_id);
        if (user == null) {
            throw new NoInfoReceivedException();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{user_id}")
    public ResponseEntity<User> updateUser(@PathVariable Long user_id, @RequestBody UserDTORequest userDetails){
        User updateUser =userService.updateUser(user_id, userDetails);
        if(updateUser ==null){
            throw new NoInfoReceivedException();
        }
        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long user_id){
        userService.deleteUser(user_id);
        return ResponseEntity.noContent().build();
    }
}
