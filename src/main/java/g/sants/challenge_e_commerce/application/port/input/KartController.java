package g.sants.challenge_e_commerce.application.port.input;

import g.sants.challenge_e_commerce.adapter.messages.methods.MessageCategory;
import g.sants.challenge_e_commerce.application.dto.ItemDTORequest;
import g.sants.challenge_e_commerce.application.dto.KartDTORequest;
import g.sants.challenge_e_commerce.application.dto.KartDTOResponse;
import g.sants.challenge_e_commerce.application.dto.UserDTOResponse;
import g.sants.challenge_e_commerce.application.service.KartService;
import g.sants.challenge_e_commerce.application.service.StorageService;
import g.sants.challenge_e_commerce.application.service.UserService;
import g.sants.challenge_e_commerce.domain.Item;
import g.sants.challenge_e_commerce.domain.Kart;
import g.sants.challenge_e_commerce.domain.Storage;
import g.sants.challenge_e_commerce.domain.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    StorageService storageService;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/user/{user_id}")
    public List<Kart> getAllKarts() {
        return kartService.getAllKarts();
    }

    @GetMapping("/user/{user_id}/kart/{kart_id}")
    public ResponseEntity<KartDTOResponse> getKartById(@PathVariable Long user_id,
                                                       @PathVariable Long kart_id) {
        UserDTOResponse user = userService.getUser(user_id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        KartDTOResponse kart = kartService.getKart(kart_id);
        if (kart == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(kart);
    }

    @PostMapping("/user/{user_id}")
    public ResponseEntity<Object> createKart(@PathVariable Long user_id, @RequestBody List<Item> items) throws Exception {
        Kart kart = new Kart();

        for (Item item : items) {
            kart.addItem(item);
        }

        Optional<User> user = userService.getUserForKart(user_id);
        Kart createdKart;
        if (user.isPresent()) {
            kart.setUser(user.get());

            for (int i =0;i<kart.getItems().size();i++) {
                Item itemCheck = kart.getItems().get(i);
                Storage itemVer = storageService.findItemByName(itemCheck.getItemName());
                if (itemVer != null & itemVer.getName().equalsIgnoreCase(itemCheck.getItemName())) {
                    int storageCont = Integer.signum(itemVer.getQuantity() - itemCheck.getQuantity());
                    switch (storageCont) {
                        case 1:
                            itemVer.setQuantity(itemVer.getQuantity()-itemCheck.getQuantity());
                            storageService.saveItemInStorage(itemVer);
                            createdKart = kartService.createKart(user_id, kart);

                            rabbitTemplate.convertAndSend(MessageCategory.ORDER_CREATED,kart.getUser().getName());
                            return ResponseEntity.status(HttpStatus.CREATED).body(createdKart);

                        case 0:
                            itemVer.setQuantity(-itemCheck.getQuantity());
                            storageService.saveItemInStorage(itemVer);
                            return ResponseEntity.status(HttpStatus.CREATED).body("Order was made, but stock is now empty");

                        case -1:
                            return ResponseEntity.badRequest().body("Error adding to kart, item"+ itemVer.getName()+
                                    " is out of stock");
                    }
                }
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Items in stock");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PutMapping("/add/user/{user_id}/kart/{kart_id}")
    public ResponseEntity<Object> updatedKart(@PathVariable Long user_id, @PathVariable Long kart_id,
                                            @RequestBody KartDTORequest kartDetails) {
        KartDTOResponse kart = kartService.getKart(kart_id);
        if (kart == null) {
            return ResponseEntity.notFound().build();
        }

        String kartValidate = kart.status();
        if ("PENDING".equals(kartValidate)) {
            Kart updateKart = kartService.updateKart(user_id, kart_id, kartDetails);
             if (updateKart == null) {
                return ResponseEntity.notFound().build();
            }

            for(int i =0;i<kart.items().size();i++){
                ItemDTORequest itemCheck = kartDetails.items().get(i);
                Storage itemVer = storageService.findItemByName(itemCheck.itemName());
                if (itemVer != null & itemVer.getName().equalsIgnoreCase(itemCheck.itemName())) {
                    int storageCont = Integer.signum(itemVer.getQuantity() - itemCheck.quantity());
                    switch (storageCont) {
                        case 1:
                            itemVer.setQuantity(itemVer.getQuantity()-itemCheck.quantity());
                            storageService.saveItemInStorage(itemVer);
                            return ResponseEntity.ok(updateKart);
                        case 0: 
                            itemVer.setQuantity(0);
                            storageService.saveItemInStorage(itemVer);
                            ResponseEntity.status(HttpStatus.ACCEPTED).body("Order was made, but stock is now empty");
                            return ResponseEntity.ok(updateKart);
                        case -1:
                            return ResponseEntity.badRequest().body("Error adding to kart, item"+ itemVer.getName()+
                                    " is out of stock");
                    }
                }
            }
        } return ResponseEntity.badRequest().build();
    }

    @PutMapping("/remove/user/{user_id}/kart/{kart_id}")
    public ResponseEntity<Object> deletedKart(@PathVariable Long user_id, @PathVariable Long kart_id,
                                            @RequestBody KartDTORequest kartDetails) {
        KartDTOResponse kart = kartService.getKart(kart_id);
        if (kart == null) {
            return ResponseEntity.notFound().build();
        }
        String kartValidate = kart.status();
        if ("PENDING".equals(kartValidate)) {
            Kart updatedKart = kartService.deletedKart(user_id, kart_id, kartDetails);
            if (updatedKart == null) {
                return ResponseEntity.notFound().build();
            }
            for(int i =0;i<kart.items().size();i++){
                ItemDTORequest itemCheck = kart.items().get(i);
                Item itemVer = updatedKart.getItems().get(i);
                if (itemVer != null & itemVer.getItemName().equalsIgnoreCase(itemCheck.itemName())) {
                    itemVer.setQuantity(+1);
                    Storage savedItem = storageService.findItemByName(itemVer.getItemName());
                    savedItem.setQuantity(itemVer.getQuantity());
                    storageService.saveItemInStorage(savedItem);
                    return ResponseEntity.ok(updatedKart);
                }
            }
        } return ResponseEntity.badRequest().build();
    }

    @PutMapping("/cancel/user/{user_id}/kart/{kart_id}")
    public ResponseEntity<Kart> newStatus(@PathVariable Long user_id, @PathVariable Long kart_id,
                                          @RequestBody KartDTORequest kartDetails) {
        KartDTOResponse kart = kartService.getKart(kart_id);
        if (kart == null) {
            return ResponseEntity.notFound().build();
        }
        String kartValidate = kart.status();
        if ("PENDING".equals(kartValidate)) {
            Kart updatedKart = kartService.deleteKart(user_id, kart_id, kartDetails);
            if (updatedKart == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updatedKart);
        } else return ResponseEntity.badRequest().build();
    }

}