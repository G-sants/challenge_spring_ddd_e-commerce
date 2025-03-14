package g.sants.challenge_e_commerce.application.port.input;

import g.sants.challenge_e_commerce.application.dto.AuthorizationDtoRequest;
import g.sants.challenge_e_commerce.application.dto.RegisterDtoRequest;
import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import g.sants.challenge_e_commerce.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthorizationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated AuthorizationDtoRequest data) {
       try {
           var usernamePassword = new UsernamePasswordAuthenticationToken
                   (data.email(), data.password());
           var auth = this.authenticationManager.authenticate(usernamePassword);
           return ResponseEntity.ok().build();
       }catch (BadCredentialsException e) {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Login");
       }catch (Exception e) {
           return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Authentication Failed"+ e.getMessage());
       }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Validated RegisterDtoRequest data){
        if(this.userRepository.findByEmail(data.email()) != null)
            return ResponseEntity.badRequest().body("User already registered");


        String encrytedPass = new BCryptPasswordEncoder().encode(data.password());
        User newuser = new User(data.customerID(),data.name(),
                data.lastName(),data.email(),encrytedPass,data.category());

        this.userRepository.save(newuser);
        return ResponseEntity.ok().build();

    }
}
