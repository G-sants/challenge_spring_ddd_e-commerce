package g.sants.challenge_e_commerce.services_test;

import g.sants.challenge_e_commerce.application.dto.CartDTORequest;
import g.sants.challenge_e_commerce.application.dto.CartDTOResponse;
import g.sants.challenge_e_commerce.application.dto.ItemDTORequest;
import g.sants.challenge_e_commerce.application.dto.UserDTOResponse;
import g.sants.challenge_e_commerce.application.port.output.CartRepository;
import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import g.sants.challenge_e_commerce.application.service.CartService;
import g.sants.challenge_e_commerce.application.service.UserService;
import g.sants.challenge_e_commerce.application.service.methods.UserCategory;
import g.sants.challenge_e_commerce.domain.Cart;
import g.sants.challenge_e_commerce.domain.Item;
import g.sants.challenge_e_commerce.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CartServicesTests {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private CartService cartService;

    @Test
    public void GetAllCart_Returns_All_Cart() {

        Cart cart1 = new Cart();
        cart1.setId(1L);

        Cart cart2 = new Cart();
        cart2.setId(2L);

        User user = new User(12312312312L, "Test", "User",
                "test@email.com", "tpassword", UserCategory.ADMIN);
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cartRepository.findAll()).thenReturn(List.of(cart1, cart2));

        List<CartDTOResponse> cartList = cartService.getAllKarts(1L);

        Assertions.assertNotNull(cartList);
        Assertions.assertEquals(2, cartList.size());
    }

    @Test
    public void GetCartById_Returns_Cart() {
        Cart cart1 = new Cart();
        cart1.setId(1L);

        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart1));

        CartDTOResponse cartResponse = cartService.getKart(1L);

        Assertions.assertNotNull(cartResponse);
        Assertions.assertEquals("PENDING", cartResponse.status());
    }

    @Test
    public void CreateCart_Creates_a_Cart() {
        Cart cart1 = new Cart();
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        when(cartRepository.save(any(Cart.class))).thenAnswer(arg -> {
            Cart savedCart1 = arg.getArgument(0);
            savedCart1.setId(1L);
            return savedCart1;
        });

        CartDTOResponse cartResponse = cartService.createKart(1L, cart1);

        Assertions.assertNotNull(cartResponse);
        Assertions.assertEquals("PENDING", cartResponse.status());
    }

    @Test
    public void DeleteCart_RemovesOneSpecifiedItemInCart() {
        Cart cart1 = new Cart();
        cart1.setId(1L);
        UserDTOResponse user = new UserDTOResponse(1L,12312312312L,
                "Test", "User", "test@email.com");

        Item itemCart1 = new Item(0.99, "Potato", 10);
        cart1.addItem(itemCart1);

        List<ItemDTORequest> itemList = new ArrayList<>();
        ItemDTORequest item1 = new ItemDTORequest("Potato", 0.99, 1);
        itemList.add(item1);

        when(userService.getUser(1L)).thenReturn(user);
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart1));

        CartDTORequest cartList = new CartDTORequest(itemList, "`PENDING");

        Cart updatedCart = cartService.deletedKart(1L, 1L, cartList);

        Assertions.assertNotNull(updatedCart);
        Assertions.assertEquals(1, updatedCart.getItems().size());
        Assertions.assertEquals(9, updatedCart.getItems().get(0).getQuantity());
    }
}