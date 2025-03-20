package g.sants.challenge_e_commerce.application.port.input;

import g.sants.challenge_e_commerce.application.dto.UserDtoResponse;
import g.sants.challenge_e_commerce.application.service.KartService;
import g.sants.challenge_e_commerce.application.service.UserService;
import g.sants.challenge_e_commerce.domain.Kart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{id}/orders")
public class KartController {

    @Autowired
    KartService kartService;
    UserService userService;

    @GetMapping
    public List<Kart> getAllIKarts() {
        return kartService.getAllKarts();
    }

    @GetMapping("/{kart_id}")
    public ResponseEntity<Kart> getKartById(@PathVariable Long id,
                                            @PathVariable Long kart_id) {
        UserDtoResponse user = userService.getUser(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        Kart kart = kartService.getKart(kart_id);
        if (kart == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(kart);
    }

    @PostMapping
    public Kart createKart(@RequestBody Kart kart) {
        return kartService.createKart(kart);
    }

    @PutMapping("/{kart_id}")
    public ResponseEntity<?> updateKart(@PathVariable Long id, @PathVariable Long kart_id,
                                        @RequestBody Kart kartDetails) {
        Kart kart = kartService.getKart(kart_id);
        if (kart == null){
            return ResponseEntity.notFound().build();
        }
        String kartValidate = kart.getStatus();
        if ("PENDING".equals(kartValidate)) {
            Kart updateKart = kartService.updateKart(id, kart_id, kartDetails);
            if (updateKart == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updateKart);
        }else return ResponseEntity.badRequest().body("Cannot Update Order." +
                "It does not have PENDING status");
    }


    @DeleteMapping("/{kart_id}")
    public ResponseEntity<Void> deleteKart(@PathVariable Long id,@PathVariable Long kart_id){
        kartService.deleteKart(id,kart_id);
        return ResponseEntity.noContent().build();
    }
}
