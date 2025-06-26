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

    @Autowired
    public CartService(CartRepository cartRepository,UserRepository userRepository,
                       UserService userService){
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    public List<CartDTOResponse> getAllCarts(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            return cartRepository.findAll ().stream()
                .map(CartDTOResponse::new)
                .collect(Collectors.toList());
        }
        throw new UserNotFoundException();
    }

    public CartDTOResponse getCart(Long id) {
        Optional<Cart> kart = cartRepository.findById(id);
        return kart.map(CartDTOResponse::new)
                .orElseThrow(() -> new RuntimeException("Order not Found"));
    }

    public CartDTOResponse createCart(Long id, Cart cart) {
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

    public Cart updateCart(Long id, Long cartId, CartDTORequest cartDetails) {
        userRepository.findById(id);
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(OrderNotFoundException::new);
        Cart.updateCartItems(cart, cartDetails);
        return cartRepository.save(cart);
    }

    public Cart deletedCart(Long userId, Long cartId, CartDTORequest cartDetails) {
        userRepository.findById(userId);
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(OrderNotFoundException::new);
        Cart.processItemsInCart(cart, cartDetails);
        updateCartTotals(cart);
        return cartRepository.save(cart);
    }

    public Cart deleteCart(Long userId, Long cartId, CartDTORequest statusDetails) {
        userRepository.findById(userId);
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(OrderNotFoundException::new);
        String checkStatus = statusDetails.status();
        if (checkStatus.equalsIgnoreCase("cancel")) {
            cart.setStatus("CANCELLED");
            cartRepository.save(cart);
            return cart;
        } else {
            throw new OrderCancelledException();
        }
    }

    public void payedCart(Long userId, Long cartId){
        userRepository.findById((userId));
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(OrderNotFoundException::new);
        cart.setStatus("PAYED");
        cartRepository.save(cart);
    }
}
