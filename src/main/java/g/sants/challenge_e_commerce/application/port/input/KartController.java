package g.sants.challenge_e_commerce.application.port.input;

import g.sants.challenge_e_commerce.application.dto.ItemDTORequest;
import g.sants.challenge_e_commerce.application.dto.KartDTORequest;
import g.sants.challenge_e_commerce.application.dto.KartDTOResponse;
import g.sants.challenge_e_commerce.application.dto.UserDTOResponse;
import g.sants.challenge_e_commerce.application.port.output.ItemRepository;
import g.sants.challenge_e_commerce.application.port.output.StorageRepository;
import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import g.sants.challenge_e_commerce.application.service.KartService;
import g.sants.challenge_e_commerce.application.service.UserService;
import g.sants.challenge_e_commerce.domain.Item;
import g.sants.challenge_e_commerce.domain.Kart;
import g.sants.challenge_e_commerce.domain.Storage;
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
    @Autowired
    private StorageRepository storageRepository;
    @Autowired
    ItemRepository itemRepository;

    @GetMapping("/{user_id}")
    public List<Kart> getAllKarts() {
        return kartService.getAllKarts();
    }

    @GetMapping("/{user_id}/{kart_id}")
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

    @PostMapping("/{user_id}")
    public ResponseEntity<Object> createKart(@PathVariable Long user_id, @RequestBody List<Item> items) {
        Kart kart = new Kart();

        for (Item item : items) {
            kart.addItem(item);
        }

        Optional<User> user = userRepository.findById(user_id);
        Kart createdKart;
        if (user.isPresent()) {
            kart.setUser(user.get());

            for (int i =0;i<kart.getItems().size();i++) {
                Item itemCheck = kart.getItems().get(i);
                Storage itemVer = storageRepository.findByName(itemCheck.getItemName());
                if (itemVer != null & itemVer.getName().equalsIgnoreCase(itemCheck.getItemName())) {
                    int storageCont = Integer.signum(itemVer.getQuantity() - itemCheck.getQuantity());
                    switch (storageCont) {
                        case 1:
                            itemVer.setQuantity(itemVer.getQuantity()-itemCheck.getQuantity());
                            storageRepository.save(itemVer);
                            createdKart = kartService.createKart(user_id, kart);
                            return ResponseEntity.status(HttpStatus.CREATED).body(createdKart);

                        case 0:
                            itemVer.setQuantity(-itemCheck.getQuantity());
                            storageRepository.save(itemVer);
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

    @PutMapping("/add/{user_id}/{kart_id}")
    public ResponseEntity<Object> updatedKart(@PathVariable Long user_id, @PathVariable Long kart_id,
                                            @RequestBody KartDTORequest kartDetails) {
        KartDTOResponse kart = kartService.getKart(kart_id);
        if (kart == null) {
            return ResponseEntity.notFound().build();
        }

        /*if(kartDetails.items() = null){
            kartDetails.s
        }*/  //AJUSTAR PARA QUANDO A LISTA ESTIVER VAZIA ELE PREENCHER POR ENQUANTO N TA FAZENDO

        String kartValidate = kart.status();
        if ("PENDING".equals(kartValidate)) {
            Kart updateKart = kartService.updateKart(user_id, kart_id, kartDetails);
            if (updateKart == null) {
                return ResponseEntity.notFound().build();
            }
            for(int i =0;i<kart.items().size();i++){
                ItemDTORequest itemCheck = kart.items().get(i);
                Storage itemVer = storageRepository.findByName(itemCheck.itemName());
                if (itemVer != null & itemVer.getName().equalsIgnoreCase(itemCheck.itemName())) {
                    int storageCont = Integer.signum(itemVer.getQuantity() - itemCheck.quantity());
                    switch (storageCont) {
                        case 1:
                            itemVer.setQuantity(-itemCheck.quantity());
                            storageRepository.save(itemVer);
                            return ResponseEntity.ok(updateKart);
                        case 0:
                            itemVer.setQuantity(-itemCheck.quantity());
                            storageRepository.save(itemVer);
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

    @PutMapping("/remove/{user_id}/{kart_id}")
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
                    Storage savedItem = storageRepository.findByName(itemVer.getItemName());
                    savedItem.setQuantity(itemVer.getQuantity());
                    storageRepository.save(savedItem);
                    return ResponseEntity.ok(updatedKart);
                }
            }
        } return ResponseEntity.badRequest().build();
    }

    @PutMapping("/delete/{user_id}/{kart_id}")
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