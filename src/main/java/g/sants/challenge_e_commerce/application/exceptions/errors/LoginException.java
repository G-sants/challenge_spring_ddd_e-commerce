package g.sants.challenge_e_commerce.application.exceptions.errors;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class LoginException extends UsernameNotFoundException{

    public LoginException() {
        super("Invalid Login");
    }

    public LoginException(String msg) {super(msg);}
}
