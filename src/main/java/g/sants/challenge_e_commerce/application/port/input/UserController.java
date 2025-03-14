package g.sants.challenge_e_commerce.application.port.input;

import g.sants.challenge_e_commerce.application.dto.UserDtoRequest;
import g.sants.challenge_e_commerce.application.dto.UserDtoResponse;
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
    public List<UserDtoResponse> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDtoResponse> getUsersById(@PathVariable Long id){
        UserDtoResponse user = userService.getUser(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDtoRequest userDetails){
        User updateUser =userService.updateUser(id, userDetails);
        if(updateUser ==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
