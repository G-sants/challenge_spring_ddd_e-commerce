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
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class CartController {

    private final CartService cartService;
    private final UserService userService;
    private final StorageService storageService;
    private final RabbitTemplate rabbitTemplate;
    private final RestTemplate restTemplate;

    @Autowired
    public CartController(
            CartService cartService,
            UserService userService,
            StorageService storageService,
            RabbitTemplate rabbitTemplate, RestTemplate restTemplate) {
        this.cartService = cartService;
        this.userService = userService;
        this.storageService = storageService;
        this.rabbitTemplate = rabbitTemplate;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/user/{userId}")
    public List<CartDTOResponse> getAllcarts(@PathVariable Long userId) {
        return cartService.getAllCarts(userId);
    }

    @GetMapping("/user/{userId}/cart/{cartId}")
    public ResponseEntity<CartDTOResponse> getcartById(@PathVariable Long userId,
                                                       @PathVariable Long cartId) {
        UserDTOResponse user = userService.getUser(userId);
        if (user == null) {
            throw new UserNotFoundException();
        }
        CartDTOResponse cart = cartService.getCart(cartId);
        if (cart == null) {
            throw new OrderNotFoundException();
        }
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<Object> createcart(@PathVariable Long userId,
                                             @RequestBody List<Item> items) {
        Cart cart = new Cart();

        for (Item item : items) {
            cart.addItem(item);
        }

        Optional<User> user = userService.getUserForCart(userId);
        CartDTOResponse createdcart;
        if (user.isPresent()) {
            cart.setUser(user.get());

            for (int i =0;i<cart.getItems().size();i++) {
                Item itemCheck = cart.getItems().get(i);
                Storage itemVer = storageService.findItemByName(itemCheck.getItemName());
                if (itemVer != null && itemVer.getName().equalsIgnoreCase(itemCheck.getItemName())) {
                    int storageCont = Integer.signum(itemVer.getQuantity() - itemCheck.getQuantity());
                    switch (storageCont) {
                        case 1:
                            itemVer.setQuantity(itemVer.getQuantity()-itemCheck.getQuantity());
                            storageService.saveItemInStorage(itemVer);
                            createdcart = cartService.createCart(userId, cart);

                            rabbitTemplate.convertAndSend(MessageCategory.ORDER_CREATED,cart.getUser().getName());
                            return ResponseEntity.status(HttpStatus.CREATED).body(createdcart);

                        case 0:
                            itemVer.setQuantity(-itemCheck.getQuantity());
                            storageService.saveItemInStorage(itemVer);
                            return ResponseEntity.status(HttpStatus.CREATED).body("Order was made, but stock is now empty");

                        case -1:
                            return ResponseEntity.status(HttpStatus.INSUFFICIENT_STORAGE).body("Error adding to cart, item"+ itemVer.getName()+
                                    " is out of stock");
                    }
                }
            }

            throw new ItemNotFoundException();
        } else {
            throw new UserNotFoundException();
        }
    }

    @PostMapping("/checkout/user/{userId}/order/{cartId}")
    public ResponseEntity<UUID> sentCheckOutOrder(@PathVariable Long userId,@PathVariable Long cartId) {

        UserDTOResponse user = userService.getUser(userId);
        if (user == null) {
            throw new UserNotFoundException();
        }
        CartDTOResponse cart = cartService.getCart(cartId);
        if (cart == null) {
            throw new OrderNotFoundException();
        }

        String cartValidate = cart.status();
        UUID id;
        if ("PENDING".equals(cartValidate)) {
            String url = "http://localhost:8081/checkout/order-new";
            id = cartService.sentCheckoutOrder(url, cartId);
        } else throw new OrderCancelledException();

        return ResponseEntity.ok(id);
    }

    @PutMapping("/add/user/{userId}/cart/{cartId}")
    public ResponseEntity<Object> updatedcart(@PathVariable Long userId, @PathVariable Long cartId,
                                              @RequestBody CartDTORequest cartDetails) {

        UserDTOResponse user = userService.getUser(userId);
        if (user == null){
            throw new UserNotFoundException();
        }

        CartDTOResponse cart = cartService.getCart(cartId);
        if (cart == null) {
            throw new OrderNotFoundException();
        }

        String cartValidate = cart.status();
        if ("PENDING".equals(cartValidate)) {
            Cart updatecart = cartService.updateCart(userId, cartId, cartDetails);
             if (updatecart == null) {
                return ResponseEntity.notFound().build();
            }

            for(int i =0;i<cart.items().size();i++){
                ItemDTORequest itemCheck = cartDetails.items().get(i);
                Storage itemVer = storageService.findItemByName(itemCheck.itemName());
                if (itemVer != null && itemVer.getName().equalsIgnoreCase(itemCheck.itemName())) {
                    int storageCont = Integer.signum(itemVer.getQuantity() - itemCheck.quantity());
                    switch (storageCont) {
                        case 1:
                            itemVer.setQuantity(itemVer.getQuantity()-itemCheck.quantity());
                            storageService.saveItemInStorage(itemVer);
                            return ResponseEntity.ok(updatecart);
                        case 0: 
                            itemVer.setQuantity(0);
                            storageService.saveItemInStorage(itemVer);
                            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Order was made, but stock is now empty");
                        case -1:
                            return ResponseEntity.status(HttpStatus.INSUFFICIENT_STORAGE).body("Error adding to cart, item"+ itemVer.getName()+
                                    " is out of stock");
                    }
                }
            }
        } else throw new OrderCancelledException();
        return ResponseEntity.badRequest().body("Invalid cart status or no items processed.");
    }

    @PutMapping("/remove/user/{userId}/cart/{cartId}")
    public ResponseEntity<Object> deletedcart(@PathVariable Long userId, @PathVariable Long cartId,
                                              @RequestBody CartDTORequest cartDetails) {

        UserDTOResponse user = userService.getUser(userId);
        if (user == null){
            throw new UserNotFoundException();
        }

        CartDTOResponse cart = cartService.getCart(cartId);
        if (cart == null) {
            throw new OrderNotFoundException();
        }

        String cartValidate = cart.status();
        if ("PENDING".equals(cartValidate)) {
            Cart updatedcart = cartService.deletedCart(userId, cartId, cartDetails);
            if (updatedcart == null) {
                return ResponseEntity.notFound().build();
            }
            for(int i =0;i<cart.items().size();i++){
                ItemDTORequest itemCheck = cart.items().get(i);
                Item itemVer = updatedcart.getItems().get(i);
                if (itemVer != null && itemVer.getItemName().equalsIgnoreCase(itemCheck.itemName())) {
                    itemVer.setQuantity(+1);
                    Storage savedItem = storageService.findItemByName(itemVer.getItemName());
                    savedItem.setQuantity(itemVer.getQuantity());
                    storageService.saveItemInStorage(savedItem);
                    return ResponseEntity.ok(updatedcart);
                }
            }
        } throw new OrderCancelledException();
    }

    @PutMapping("/cancel/user/{userId}/cart/{cartId}")
    public ResponseEntity<Cart> newStatus(@PathVariable Long userId, @PathVariable Long cartId,
                                          @RequestBody CartDTORequest cartDetails) {

        UserDTOResponse user = userService.getUser(userId);
        if (user == null){
            throw new UserNotFoundException();
        }

        CartDTOResponse cart = cartService.getCart(cartId);
        if (cart == null) {
            throw new OrderNotFoundException();
        }

        String cartValidate = cart.status();
        if ("PENDING".equals(cartValidate)) {
            Cart updatedcart = cartService.deleteCart(userId, cartId, cartDetails);
            if (updatedcart == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updatedcart);
        } throw new OrderCancelledException();
    }

}