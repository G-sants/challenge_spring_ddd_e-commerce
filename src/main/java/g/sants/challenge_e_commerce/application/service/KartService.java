package g.sants.challenge_e_commerce.application.service;

import g.sants.challenge_e_commerce.application.dto.KartDTORequest;
import g.sants.challenge_e_commerce.application.dto.KartDTOResponse;
import g.sants.challenge_e_commerce.application.port.output.KartRepository;
import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import g.sants.challenge_e_commerce.domain.Kart;
import g.sants.challenge_e_commerce.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KartService {

    @Autowired
    private KartRepository kartRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Kart> getAllKarts() {
        return kartRepository.findAll();
    }

    public KartDTOResponse getKart(Long id) {
        Optional<Kart> kart = kartRepository.findById(id);
        return kart.map(KartDTOResponse::new)
                .orElseThrow(() -> new RuntimeException("Order not Found"));
    }

    public Kart createKart(Long id,Kart kart) {
           Optional<User> user = userRepository.findById(id);
           if(user.isPresent()){
               kart.setUser(user.get());
               return kartRepository.save(kart);
           }
        return null;
    }

    public Kart updateKart(Long id, Long kart_id, KartDTORequest kartDetails) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not find with id" + id));
            if (user != null) {
                try {
                    Optional<Kart> optionalKart = kartRepository.findById(kart_id);
                        if(optionalKart.isPresent()) {
                            Kart kart = optionalKart.get();
                            return kartRepository.save(kart);
                        }else {
                            throw new RuntimeException("Order not Found within User" + id);
                        }
                } catch (Exception e) {
                    throw new RuntimeException("Error finding order" + e.getMessage());
                }
            }
        }catch (Exception e){
            throw new RuntimeException("Error finding user" + e.getMessage());
        }
        return null;
    }

    public void deleteKart(Long id, Long kart_id) {
        try {
            Optional<User> user = Optional.ofNullable(userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not Found with this id: " + id)));
                Optional<Optional<Kart>> kart = Optional.ofNullable(Optional.ofNullable(kartRepository.findById(kart_id))
                    .orElseThrow(() -> new RuntimeException("Order not found with id" + kart_id)));
                kartRepository.deleteById(kart_id);
        }catch (Exception e) {
            throw new RuntimeException("Error deleting order" + e.getMessage());
        }
    }

}
