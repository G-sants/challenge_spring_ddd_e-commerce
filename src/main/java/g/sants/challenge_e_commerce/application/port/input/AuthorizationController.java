package g.sants.challenge_e_commerce.application.port.input;

import g.sants.challenge_e_commerce.application.dto.AuthorizationDTORequest;
import g.sants.challenge_e_commerce.application.dto.LoginDTOResponse;
import g.sants.challenge_e_commerce.application.dto.RegisterDTORequest;
import g.sants.challenge_e_commerce.application.dto.UserDTOResponse;
import g.sants.challenge_e_commerce.application.service.AuthorizationService;
import g.sants.challenge_e_commerce.application.service.TokenService;
import g.sants.challenge_e_commerce.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final AuthorizationService authorizationService;

    @Autowired
    public AuthorizationController(
            AuthenticationManager authenticationManager,
            TokenService tokenService,
            AuthorizationService authorizationService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.authorizationService = authorizationService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated AuthorizationDTORequest data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken
                (data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginDTOResponse(token));
    }

    @PostMapping("/register")
    public UserDTOResponse register(@RequestBody @Validated RegisterDTORequest data){
        return authorizationService.registerNewUser(data);
    }
}
