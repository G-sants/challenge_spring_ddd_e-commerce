package g.sants.challenge_e_commerce.application.port.input;

import g.sants.challenge_e_commerce.adapter.messages.methods.MessageCategory;
import g.sants.challenge_e_commerce.application.dto.ItemDTORequest;
import g.sants.challenge_e_commerce.application.dto.CartDTORequest;
import g.sants.challenge_e_commerce.application.dto.CartDTOResponse;
import g.sants.challenge_e_commerce.application.dto.UserDTOResponse;
import g.sants.challenge_e_commerce.application.exceptions.errors.ItemNotFoundException;
import g.sants.challenge_e_commerce.application.exceptions.errors.OrderCancelledException;
import g.sants.challenge_e_commerce.application.exceptions.errors.OrderNotFoundException;
import g.sants.challenge_e_commerce.application.exceptions.errors.UserNotFoundException;
import g.sants.challenge_e_commerce.application.service.CartService;
import g.sants.challenge_e_commerce.application.service.StorageService;
import g.sants.challenge_e_commerce.application.service.UserService;
import g.sants.challenge_e_commerce.domain.Item;
import g.sants.challenge_e_commerce.domain.Cart;
import g.sants.challenge_e_commerce.domain.Storage;
import g.sants.challenge_e_commerce.domain.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class CartController {

    private final CartService kartService;
    private final UserService userService;
    private final StorageService storageService;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public CartController(
            CartService kartService,
            UserService userService,
            StorageService storageService,
            RabbitTemplate rabbitTemplate) {
        this.kartService = kartService;
        this.userService = userService;
        this.storageService = storageService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/user/{userId}")
    public List<CartDTOResponse> getAllKarts(@PathVariable Long userId) {
        return kartService.getAllCarts(userId);
    }

    @GetMapping("/user/{userId}/kart/{kartId}")
    public ResponseEntity<CartDTOResponse> getKartById(@PathVariable Long userId,
                                                       @PathVariable Long kartId) {
        UserDTOResponse user = userService.getUser(userId);
        if (user == null) {
            throw new UserNotFoundException();
        }
        CartDTOResponse kart = kartService.getCart(kartId);
        if (kart == null) {
            throw new OrderNotFoundException();
        }
        return ResponseEntity.ok(kart);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<Object> createKart(@PathVariable Long userId,
                                             @RequestBody List<Item> items) {
        Cart kart = new Cart();

        for (Item item : items) {
            kart.addItem(item);
        }

        Optional<User> user = userService.getUserForKart(userId);
        CartDTOResponse createdKart;
        if (user.isPresent()) {
            kart.setUser(user.get());

            for (int i =0;i<kart.getItems().size();i++) {
                Item itemCheck = kart.getItems().get(i);
                Storage itemVer = storageService.findItemByName(itemCheck.getItemName());
                if (itemVer != null && itemVer.getName().equalsIgnoreCase(itemCheck.getItemName())) {
                    int storageCont = Integer.signum(itemVer.getQuantity() - itemCheck.getQuantity());
                    switch (storageCont) {
                        case 1:
                            itemVer.setQuantity(itemVer.getQuantity()-itemCheck.getQuantity());
                            storageService.saveItemInStorage(itemVer);
                            createdKart = kartService.createCart(userId, kart);

                            rabbitTemplate.convertAndSend(MessageCategory.ORDER_CREATED,kart.getUser().getName());
                            return ResponseEntity.status(HttpStatus.CREATED).body(createdKart);

                        case 0:
                            itemVer.setQuantity(-itemCheck.getQuantity());
                            storageService.saveItemInStorage(itemVer);
                            return ResponseEntity.status(HttpStatus.CREATED).body("Order was made, but stock is now empty");

                        case -1:
                            return ResponseEntity.status(HttpStatus.INSUFFICIENT_STORAGE).body("Error adding to kart, item"+ itemVer.getName()+
                                    " is out of stock");
                    }
                }
            }

            throw new ItemNotFoundException();
        } else {
            throw new UserNotFoundException();
        }
    }

    @PutMapping("/add/user/{userId}/kart/{kartId}")
    public ResponseEntity<Object> updatedKart(@PathVariable Long userId, @PathVariable Long kartId,
                                              @RequestBody CartDTORequest kartDetails) {

        UserDTOResponse user = userService.getUser(userId);
        if (user == null){
            throw new UserNotFoundException();
        }

        CartDTOResponse kart = kartService.getCart(kartId);
        if (kart == null) {
            throw new OrderNotFoundException();
        }

        String kartValidate = kart.status();
        if ("PENDING".equals(kartValidate)) {
            Cart updateKart = kartService.updateCart(userId, kartId, kartDetails);
             if (updateKart == null) {
                return ResponseEntity.notFound().build();
            }

            for(int i =0;i<kart.items().size();i++){
                ItemDTORequest itemCheck = kartDetails.items().get(i);
                Storage itemVer = storageService.findItemByName(itemCheck.itemName());
                if (itemVer != null && itemVer.getName().equalsIgnoreCase(itemCheck.itemName())) {
                    int storageCont = Integer.signum(itemVer.getQuantity() - itemCheck.quantity());
                    switch (storageCont) {
                        case 1:
                            itemVer.setQuantity(itemVer.getQuantity()-itemCheck.quantity());
                            storageService.saveItemInStorage(itemVer);
                            return ResponseEntity.ok(updateKart);
                        case 0: 
                            itemVer.setQuantity(0);
                            storageService.saveItemInStorage(itemVer);
                            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Order was made, but stock is now empty");
                        case -1:
                            return ResponseEntity.status(HttpStatus.INSUFFICIENT_STORAGE).body("Error adding to kart, item"+ itemVer.getName()+
                                    " is out of stock");
                    }
                }
            }
        } throw new OrderCancelledException();
    }

    @PutMapping("/remove/user/{userId}/kart/{kartId}")
    public ResponseEntity<Object> deletedKart(@PathVariable Long userId, @PathVariable Long kartId,
                                              @RequestBody CartDTORequest kartDetails) {

        UserDTOResponse user = userService.getUser(userId);
        if (user == null){
            throw new UserNotFoundException();
        }

        CartDTOResponse kart = kartService.getCart(kartId);
        if (kart == null) {
            throw new OrderNotFoundException();
        }

        String kartValidate = kart.status();
        if ("PENDING".equals(kartValidate)) {
            Cart updatedKart = kartService.deletedCart(userId, kartId, kartDetails);
            if (updatedKart == null) {
                return ResponseEntity.notFound().build();
            }
            for(int i =0;i<kart.items().size();i++){
                ItemDTORequest itemCheck = kart.items().get(i);
                Item itemVer = updatedKart.getItems().get(i);
                if (itemVer != null && itemVer.getItemName().equalsIgnoreCase(itemCheck.itemName())) {
                    itemVer.setQuantity(+1);
                    Storage savedItem = storageService.findItemByName(itemVer.getItemName());
                    savedItem.setQuantity(itemVer.getQuantity());
                    storageService.saveItemInStorage(savedItem);
                    return ResponseEntity.ok(updatedKart);
                }
            }
        } throw new OrderCancelledException();
    }

    @PutMapping("/cancel/user/{userId}/kart/{kartId}")
    public ResponseEntity<Cart> newStatus(@PathVariable Long userId, @PathVariable Long kartId,
                                          @RequestBody CartDTORequest kartDetails) {

        UserDTOResponse user = userService.getUser(userId);
        if (user == null){
            throw new UserNotFoundException();
        }

        CartDTOResponse kart = kartService.getCart(kartId);
        if (kart == null) {
            throw new OrderNotFoundException();
        }

        String kartValidate = kart.status();
        if ("PENDING".equals(kartValidate)) {
            Cart updatedKart = kartService.deleteCart(userId, kartId, kartDetails);
            if (updatedKart == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updatedKart);
        } throw new OrderCancelledException();
    }

}