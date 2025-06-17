package g.sants.challenge_e_commerce.services_test;

import g.sants.challenge_e_commerce.application.dto.CartDTORequest;
import g.sants.challenge_e_commerce.application.dto.CartDTOResponse;
import g.sants.challenge_e_commerce.application.dto.ItemDTORequest;
import g.sants.challenge_e_commerce.application.port.output.CartRepository;
import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import g.sants.challenge_e_commerce.application.service.CartService;
import g.sants.challenge_e_commerce.application.service.methods.UserCategory;
import g.sants.challenge_e_commerce.domain.Cart;
import g.sants.challenge_e_commerce.domain.Item;
import g.sants.challenge_e_commerce.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CartServicesTests {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CartService cartService;


    @Test
    void GetAllCart_Returns_All_Cart() {

        Cart cart1 = new Cart();
        cart1.setId(1L);

        Cart cart2 = new Cart();
        cart2.setId(2L);

        User user = new User(12312312312L, "Test", "User",
                "test@email.com", "tpassword", UserCategory.ADMIN);
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cartRepository.findAll()).thenReturn(List.of(cart1, cart2));

        List<CartDTOResponse> cartList = cartService.getAllCarts(1L);

        Assertions.assertNotNull(cartList);
        Assertions.assertEquals(2, cartList.size());
    }

    @Test
    void GetCartById_Returns_Cart() {
        Cart cart1 = new Cart();
        cart1.setId(1L);

        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart1));

        CartDTOResponse cartResponse = cartService.getCart(1L);

        Assertions.assertNotNull(cartResponse);
        Assertions.assertEquals("PENDING", cartResponse.status());
    }

    @Test
    void CreateCart_Creates_a_Cart() {
        Cart cart1 = new Cart();
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        when(cartRepository.save(any(Cart.class))).thenAnswer(arg -> {
            Cart savedCart1 = arg.getArgument(0);
            savedCart1.setId(1L);
            return savedCart1;
        });

        CartDTOResponse cartResponse = cartService.createCart(1L, cart1);

        Assertions.assertNotNull(cartResponse);
        Assertions.assertEquals("PENDING", cartResponse.status());
    }

    @Test
    void UpdateCart_UpdatesItemsInCart() {
        User user = new User(12312312312L,
                "Test", "User", "test@email.com","tpassword");
        user.setId(1L);

        Cart cart1 = new Cart();
        cart1.setId(1L);
        Item itemCart1 = new Item(0.99, "Potato", 10);
        cart1.addItem(itemCart1);

        List<ItemDTORequest> itemList = new ArrayList<>();
        ItemDTORequest item1 = new ItemDTORequest("Potato", 0.99, 1);
        itemList.add(item1);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart1));
        when(cartRepository.save(any(Cart.class))).thenAnswer(arg ->
                arg.getArgument(0));


        CartDTORequest cartList = new CartDTORequest(itemList, "`PENDING");

        Cart updatedCart = cartService.updateCart(1L, 1L, cartList);

        Assertions.assertNotNull(updatedCart);
        Assertions.assertEquals(1, updatedCart.getItems().size());
        Assertions.assertEquals(11, updatedCart.getItems().get(0).getQuantity());
    }

    @Test
    void DeleteCart_RemovesOneSpecifiedItemInCart() {
        User user = new User(12312312312L,
                "Test", "User", "test@email.com","tpassword");
        user.setId(1L);

        Cart cart1 = new Cart();
        cart1.setId(1L);
        Item itemCart1 = new Item(0.99, "Potato", 10);
        cart1.addItem(itemCart1);

        List<ItemDTORequest> itemList = new ArrayList<>();
        ItemDTORequest item1 = new ItemDTORequest("Potato", 0.99, 1);
        itemList.add(item1);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart1));
        when(cartRepository.save(any(Cart.class))).thenAnswer(arg ->
                arg.getArgument(0));


        CartDTORequest cartList = new CartDTORequest(itemList, "`PENDING");

        Cart updatedCart = cartService.deletedCart(1L, 1L, cartList);

        Assertions.assertNotNull(updatedCart);
        Assertions.assertEquals(1, updatedCart.getItems().size());
        Assertions.assertEquals(9, updatedCart.getItems().get(0).getQuantity());
    }

    @Test
    void DeleteCart_CancelsCart() {
        User user = new User(12312312312L,
                "Test", "User", "test@email.com","tpassword");
        user.setId(1L);

        Cart cart1 = new Cart();
        cart1.setId(1L);
        Item itemCart1 = new Item(0.99, "Potato", 10);
        cart1.addItem(itemCart1);

        List<ItemDTORequest> itemList = new ArrayList<>();
        ItemDTORequest item1 = new ItemDTORequest("Potato", 0.99, 1);
        itemList.add(item1);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart1));
        when(cartRepository.save(any(Cart.class))).thenAnswer(arg ->
                arg.getArgument(0));


        CartDTORequest cartList = new CartDTORequest(itemList, "cancel");

        Cart updatedCart = cartService.deleteCart(1L, 1L, cartList);

        Assertions.assertNotNull(updatedCart);
        Assertions.assertEquals("CANCELLED",updatedCart.getStatus());
    }

}