package g.sants.challenge_e_commerce.application.service;

import g.sants.challenge_e_commerce.application.port.output.KartRepository;
import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import g.sants.challenge_e_commerce.domain.Item;
import g.sants.challenge_e_commerce.domain.Kart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KartService {

    @Autowired
    private KartRepository kartRepository;
    private UserRepository userRepository;

    public List<Kart> getAllIKarts() {
        return kartRepository.findAll();
    }

    public Kart getKart(Long id) {
        return kartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not Found"));
    }

    public Kart createKart(Kart kart) {
        return kartRepository.save(kart);
    }

}
