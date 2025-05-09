package g.sants.challenge_e_commerce.application.service;

import g.sants.challenge_e_commerce.application.exceptions.errors.LoginException;
import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws LoginException {
        UserDetails userDetails = userRepository.findByEmail(email);
        if (userDetails == null) {
            throw new LoginException();
        }
        return userDetails;
    }

}
