package g.sants.challenge_e_commerce.services_test;

import g.sants.challenge_e_commerce.application.dto.CartDTOResponse;
import g.sants.challenge_e_commerce.application.port.output.CartRepository;
import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import g.sants.challenge_e_commerce.application.service.CartService;
import g.sants.challenge_e_commerce.domain.Cart;
import g.sants.challenge_e_commerce.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void CreateCarts_CreateACartToASpecificUser(){
        Cart cart1 = new Cart();
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        when(cartRepository.save(any(Cart.class))).thenAnswer(arg -> {
            Cart savedCart1 = arg.getArgument(0);
            savedCart1.setId(1L);
            return savedCart1;
        });

        CartDTOResponse cartResponse = cartService.createKart(1L,cart1);

        Assertions.assertNotNull(cartResponse);
        Assertions.assertEquals("PENDING",cartResponse.status());
    }

    @Test
    public void FindAll_ReturnsAllCarts(){
        Cart cart1 = new Cart();

        when(cartRepository.save(any(Cart.class))).thenAnswer(arg -> {
            Cart savedCart1 = arg.getArgument(0);
            savedCart1.setId(1L);
            return savedCart1;
        });

        cartRepository.save(cart1);

        Cart cart2 = new Cart();

        when(cartRepository.save(any(Cart.class))).thenAnswer(arg -> {
            Cart savedCart2 = arg.getArgument(0);
            savedCart2.setId(1L);
            return savedCart2;
        });

        cartRepository.save(cart2);

        when(cartRepository.findAll()).thenReturn(List.of(cart1,cart2));

        List<CartDTOResponse> cartList = cartService.getAllKarts();

        Assertions.assertNotNull(cartList);
        Assertions.assertEquals(2,cartList.size());
    }

    @Test
    public void FindCartById_ReturnSpecifiedCartWithinUser(){
        Cart cart1 = new Cart();

        when(cartRepository.save(any(Cart.class))).thenAnswer(arg -> {
            Cart savedCart = arg.getArgument(0);
            savedCart.setId(1L);
            return savedCart;
        });

        cartRepository.save(cart1);

        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart1));

        CartDTOResponse cartResponse = cartService.getKart(1L);

        Assertions.assertNotNull(cartResponse);
        Assertions.assertEquals("PENDING", cartResponse.status());
    }



/*    @Test
    public void DeleteCart_RemovesOneSpecifiedItemInCart() {
        Cart cart1 = new Cart();
        User user = new User();
        user.setId(1L);
        Item itemCart1 = new Item(0.99, "Potato", 10);
        cart1.addItem(itemCart1);

        List<ItemDTORequest> itemList = new ArrayList<>();
        ItemDTORequest item1 = new ItemDTORequest("Potato", 0.99, 1);
        itemList.add(item1);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart1));

        when(cartRepository.save(any(Cart.class))).thenAnswer(arg -> {
            Cart savedCart1 = arg.getArgument(0);
            savedCart1.setId(1L);
            return savedCart1;
        });

        cartRepository.save(cart1);

        CartDTORequest cartList = new CartDTORequest(itemList, "`PENDING");

        Cart updatedCart = cartService.deletedKart(1L, 1L, cartList);

        Assertions.assertNotNull(updatedCart);
        Assertions.assertEquals(1, updatedCart.getItems().size());
        Assertions.assertEquals(9, updatedCart.getItems().get(0).getQuantity());
    }

    @Test
    public void CancelCart_ChangeTheStatusToCancelledInCart(){
        Cart cart1 = new Cart();
        User user = new User();
        user.setId(1L);
        Item itemCart1 = new Item(0.99, "Potato", 10);
        cart1.addItem(itemCart1);

        List<ItemDTORequest> itemList = new ArrayList<>();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart1));

        when(cartRepository.save(any(Cart.class))).thenAnswer(arg -> {
            Cart savedCart1 = arg.getArgument(0);
            savedCart1.setId(1L);
            return savedCart1;
        });

        cartRepository.save(cart1);

        CartDTORequest cartList = new CartDTORequest(itemList, "cancel");

        Cart updatedCart = cartService.deleteKart(1L, 1L, cartList);

        Assertions.assertNotNull(updatedCart);
        Assertions.assertEquals("CANCELLED", updatedCart.getStatus());

    }*/
}