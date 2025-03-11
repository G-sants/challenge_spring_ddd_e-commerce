package g.sants.challenge_e_commerce.application.service;

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
    private UserRepository userRepository;

    public List<Kart> getAllKarts() {
        return kartRepository.findAll();
    }

    public Kart getKart(Long id) {
        return kartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not Found"));
    }

    public Kart createKart(Kart kart) {
        return kartRepository.save(kart);
    }

    public Kart updateKart(Long id, Long kart_id, Kart kartDetails) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not find with id" + id));
            if (user != null) {
                try {
                    Optional<Kart> optionalKart = kartRepository.findById(kart_id);
                        if(optionalKart.isPresent()) {
                            Kart kart = optionalKart.get();
                            kart.setTotalPrice(kartDetails.getTotalPrice());
                            kart.setTotalDiscount(kartDetails.getTotalDiscount());
                            kart.setTotalPriceDiscount(kartDetails.getTotalPriceDiscount());
                            kart.setDate(kartDetails.getDate());
                            kart.setStatus(kartDetails.getStatus());
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
