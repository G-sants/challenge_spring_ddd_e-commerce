package g.sants.challenge_e_commerce.application.service;

import g.sants.challenge_e_commerce.application.dto.RegisterDTORequest;
import g.sants.challenge_e_commerce.application.dto.UserDTOResponse;
import g.sants.challenge_e_commerce.application.exceptions.errors.RegistrationAlreadyDoneException;
import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import g.sants.challenge_e_commerce.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public AuthorizationService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetails userDetails = userRepository.findByEmail(email);
        if (userDetails == null) {
            throw new UsernameNotFoundException("User not Found");
        }
        return userDetails;
    }
    
    public UserDTOResponse registerNewUser(RegisterDTORequest data){
        if(this.userRepository.findByEmail(data.email()) != null)
            throw new RegistrationAlreadyDoneException();

        String encrytedPass = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.customerID(),data.name(),
                data.lastName(),data.email(),encrytedPass,data.category());

        this.userRepository.save(newUser);
        return new UserDTOResponse(newUser);
    }

}
