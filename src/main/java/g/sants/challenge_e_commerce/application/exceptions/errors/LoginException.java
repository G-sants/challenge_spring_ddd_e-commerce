package g.sants.challenge_e_commerce.application.exceptions.errors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class LoginException extends UsernameNotFoundException{

    public LoginException() {
        super("Email is not registered");
    }

    public LoginException(String msg) {super(msg);}
}
