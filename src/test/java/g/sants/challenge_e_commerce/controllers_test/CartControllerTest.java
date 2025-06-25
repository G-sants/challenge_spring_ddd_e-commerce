package g.sants.challenge_e_commerce.controllers_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import g.sants.challenge_e_commerce.application.dto.CartDTORequest;
import g.sants.challenge_e_commerce.application.dto.CartDTOResponse;
import g.sants.challenge_e_commerce.application.dto.ItemDTORequest;
import g.sants.challenge_e_commerce.application.dto.UserDTOResponse;
import g.sants.challenge_e_commerce.application.port.input.CartController;
import g.sants.challenge_e_commerce.application.port.output.CartRepository;
import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import g.sants.challenge_e_commerce.application.service.CartService;
import g.sants.challenge_e_commerce.application.service.StorageService;
import g.sants.challenge_e_commerce.application.service.TokenService;
import g.sants.challenge_e_commerce.application.service.UserService;
import g.sants.challenge_e_commerce.domain.Cart;
import g.sants.challenge_e_commerce.domain.Item;
import g.sants.challenge_e_commerce.domain.Storage;
import g.sants.challenge_e_commerce.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = CartController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private CartRepository cartRepository;

    @MockitoBean
    private TokenService tokenService;

    @MockitoBean
    private StorageService storageService;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private RabbitTemplate rabbitTemplate;

    @MockitoBean
    private CartService cartService;

    @Autowired
    private ObjectMapper objectMapper;

    List<Item> itemCartList;
    List<ItemDTORequest> itemList;
    List<CartDTOResponse> orderList;
    CartDTOResponse cart1;
    CartDTOResponse cart2;
    CartDTORequest cartRequest1;
    CartDTORequest cartRequest2;
    UserDTOResponse userDTOResponse;
    User user;
    Cart cart;
    Cart dtoCart;
    Item item;
    ItemDTORequest itemDTO;
    Storage storageItem;

    @BeforeEach
    void initCarts(){
        itemDTO = new ItemDTORequest("Potato",0.99,1);
        itemList = new ArrayList<>();
        itemList.add(itemDTO);
        cartRequest1 = new CartDTORequest(itemList,"PENDING");
        cartRequest2 = new CartDTORequest(itemList,"cancel");

        item = new Item(0.99,"Potato",10);
        itemCartList = new ArrayList<>();
        itemCartList.add(item);


        user = new User();
        user.setId(1L);

        userDTOResponse = new UserDTOResponse(1L,12312312312L,"Teste1",
                "User","test1@email.com");

        dtoCart = new Cart();
        dtoCart.setId(1L);
        dtoCart.setItems(itemCartList);

        cart1 = new CartDTOResponse(dtoCart);
        cart2 = new CartDTOResponse(dtoCart);


        orderList = new ArrayList<>();
        orderList.add(cart1);
        orderList.add(cart2);

        cart = new Cart();
        cart.setItems(itemCartList);

        storageItem = new Storage();
        storageItem.setName("Potato");
        storageItem.setQuantity(100);
    }

    @Test
    void CartController_CreatesOrder() throws Exception {
        given(userService.getUserForKart(1L)).willReturn(Optional.of(user));
        given(storageService.findItemByName("Potato")).willReturn(storageItem);
        given(cartService.createCart(anyLong(),any(Cart.class))).willReturn(cart1);

        Cart newCart = new Cart();
        newCart.addItem(new Item(0.99,"Potato",1));

        ResultActions response = mockMvc.perform(post("/orders/user/{user_id}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCart.getItems())));

        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void CartController_GetsOrderById() throws Exception {

        given(userService.getUser(any())).willReturn(userDTOResponse);
        given(cartService.getCart(any())).willReturn(cart1);

        ResultActions response = mockMvc.perform(get("/orders/user/{userId}/kart/{kartId}",
                1L,1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoCart)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void CartController_GetAllOrders() throws Exception {
        given(cartService.getAllCarts(1L)).willReturn(orderList);

        ResultActions response = mockMvc.perform(get("/orders/user/{userId}",1L)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void CartController_UpdatesItemInOrders() throws Exception{
        given(userService.getUser(1L)).willReturn(userDTOResponse);
        given(cartService.getCart(1L)).willReturn(cart1);

        given(cartService.updateCart(anyLong(), anyLong(), any(CartDTORequest.class))).willReturn(cart);
        given(storageService.findItemByName("Potato")).willReturn(storageItem);

        ResultActions response = mockMvc.perform(put("/orders/add/user/{userId}/kart/{kartId}",
                1L,1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cartRequest1)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void CartController_RemovesItemInOrders() throws Exception{
        given(userService.getUser(1L)).willReturn(userDTOResponse);
        given(cartService.getCart(1L)).willReturn(cart1);

        given(cartService.deletedCart(anyLong(), anyLong(), any(CartDTORequest.class))).willReturn(cart);
        given(storageService.findItemByName("Potato")).willReturn(storageItem);

        ResultActions response = mockMvc.perform(put("/orders/remove/user/{userId}/kart/{kartId}",
                1L,1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoCart)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void CartController_CancelOrders() throws Exception{
        given(userService.getUser(any())).willReturn(userDTOResponse);
        given(cartService.getCart(any())).willReturn(cart1);

        given(cartService.deleteCart(anyLong(), anyLong(), any(CartDTORequest.class)))
                .willReturn(cart);

        ResultActions response = mockMvc.perform(put("/orders/cancel/user/{userId}/kart/{kartId}",
                1L,1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cartRequest2)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

}
