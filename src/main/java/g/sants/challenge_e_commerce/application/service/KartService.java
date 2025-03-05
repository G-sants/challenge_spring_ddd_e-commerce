package g.sants.challenge_e_commerce.application.service;

import g.sants.challenge_e_commerce.application.port.output.KartRepository;
import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KartService {

    @Autowired
    private KartRepository kartRepository;
    private UserRepository userRepository;

}
