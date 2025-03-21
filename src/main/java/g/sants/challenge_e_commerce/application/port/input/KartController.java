package g.sants.challenge_e_commerce.application.port.input;

import g.sants.challenge_e_commerce.application.dto.KartDTORequest;
import g.sants.challenge_e_commerce.application.dto.KartDTOResponse;
import g.sants.challenge_e_commerce.application.dto.UserDTOResponse;
import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import g.sants.challenge_e_commerce.application.service.KartService;
import g.sants.challenge_e_commerce.application.service.UserService;
import g.sants.challenge_e_commerce.domain.Item;
import g.sants.challenge_e_commerce.domain.Kart;
import g.sants.challenge_e_commerce.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class KartController {

    @Autowired
    private KartService kartService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{user_id}")
    public List<Kart> getAllKarts() {
        return kartService.getAllKarts();
    }

    @GetMapping("/{user_id}/{kart_id}")
    public ResponseEntity<KartDTOResponse> getKartById(@PathVariable Long id,
                                                       @PathVariable Long kart_id) {
        UserDTOResponse user = userService.getUser(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        KartDTOResponse kart = kartService.getKart(kart_id);
        if (kart == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(kart);
    }

    @PostMapping("/{user_id}")
    public ResponseEntity<Kart> createKart(@PathVariable Long user_id, @RequestBody List<Item> items) {
        Kart kart = new Kart();

        for (Item item : items) {
            kart.addItem(item);
        }

        Optional<User> user = userRepository.findById(user_id);
        Kart createdKart;
        if (user.isPresent()) {
            kart.setUser(user.get());
            createdKart = kartService.createKart(user_id, kart);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdKart);
        } else {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{user_id}/{kart_id}")
    public ResponseEntity<Kart> updateKart(@PathVariable Long id, @PathVariable Long kart_id,
                                        @RequestBody KartDTORequest kartDetails) {
        KartDTOResponse kart = kartService.getKart(kart_id);
        if (kart == null){
            return ResponseEntity.notFound().build();
        }
        String kartValidate = kart.status();
        if ("PENDING".equals(kartValidate)) {
            Kart updateKart = kartService.updateKart(id, kart_id, kartDetails);
            if (updateKart == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updateKart);
        }else return ResponseEntity.badRequest().build();
    }


    @DeleteMapping("/{user_id}/{kart_id}")
    public ResponseEntity<Void> deleteKart(@PathVariable Long id,@PathVariable Long kart_id){
        kartService.deleteKart(id,kart_id);
        return ResponseEntity.noContent().build();
    }
}
