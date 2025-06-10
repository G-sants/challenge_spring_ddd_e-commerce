package g.sants.challenge_e_commerce.application.service;

import g.sants.challenge_e_commerce.application.dto.CartDTORequest;
import g.sants.challenge_e_commerce.application.dto.CartDTOResponse;
import g.sants.challenge_e_commerce.application.exceptions.errors.OrderCancelledException;
import g.sants.challenge_e_commerce.application.exceptions.errors.OrderNotFoundException;
import g.sants.challenge_e_commerce.application.exceptions.errors.UserNotFoundException;
import g.sants.challenge_e_commerce.application.port.output.CartRepository;
import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import g.sants.challenge_e_commerce.domain.Cart;
import g.sants.challenge_e_commerce.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static g.sants.challenge_e_commerce.domain.Cart.updateCartTotals;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public CartService(CartRepository cartRepository,UserRepository userRepository,
                       UserService userService){
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public List<CartDTOResponse> getAllKarts(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            return cartRepository.findAll ().stream()
                .map(CartDTOResponse::new)
                .collect(Collectors.toList());
        }
        throw new UserNotFoundException();
    }

    public CartDTOResponse getKart(Long id) {
        Optional<Cart> kart = cartRepository.findById(id);
        return kart.map(CartDTOResponse::new)
                .orElseThrow(() -> new RuntimeException("Order not Found"));
    }

    public CartDTOResponse createKart(Long id, Cart cart) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            cart.setUser(user.get());
            cart.setTotalPrice();
            cart.setTotalPriceDiscount();
            cart.setTotalDiscount();
            cartRepository.save(cart);
            return new CartDTOResponse(cart);
        }
        throw new UserNotFoundException();
    }

    public Cart updateKart(Long id, Long kart_id, CartDTORequest cartDetails) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            Optional<Cart> optionalKart = cartRepository.findById(kart_id);
            if (optionalKart.isPresent()) {
                Cart kart = optionalKart.get();
                Cart.updateCartItems(kart, cartDetails);
                return cartRepository.save(kart);
            }
            throw new OrderNotFoundException();
        }
        throw new UserNotFoundException();
    }

    public Cart deletedKart(Long userId, Long cartId, CartDTORequest cartDetails) {
        User user = userService.findUserById(userId);
        if(user!= null) {
            Cart cart = findCartById(cartId);
            Cart.processItemsInCart(cart, cartDetails);
            updateCartTotals(cart);
            return cartRepository.save(cart);
        }
        throw  new UserNotFoundException();
    }

    public Cart deleteKart(Long userId, Long cartId, CartDTORequest statusDetails) {
        User user = userService.findUserById(userId);
        if(user!= null) {
            Cart cart = findCartById(cartId);
            String checkStatus = statusDetails.status();
            if (checkStatus.equalsIgnoreCase("cancel")) {
                cart.setStatus("CANCELLED");
                return cartRepository.save(cart);
            } else {
                throw new OrderCancelledException();
            }
        }
        throw new UserNotFoundException();
    }

    private Cart findCartById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + cartId));
    }
}
