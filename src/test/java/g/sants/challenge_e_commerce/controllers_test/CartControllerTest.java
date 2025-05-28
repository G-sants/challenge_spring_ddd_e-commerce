package g.sants.challenge_e_commerce.controllers_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import g.sants.challenge_e_commerce.application.dto.CartDTOResponse;
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
import g.sants.challenge_e_commerce.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
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

    List<CartDTOResponse> orderList;
    CartDTOResponse cart1;
    CartDTOResponse cart2;
    UserDTOResponse userDTOResponse;
    Object object;
    User user;
    Cart cart;
    Cart dtoCart;
    Item item;

    @BeforeEach
    public void initCarts(){
        item = new Item(0.99,"Potato",12);

        user = new User();
        user.setId(1L);

        userDTOResponse = new UserDTOResponse(1L,12312312312L,"Teste1",
                "User","test1@email.com");

        dtoCart = new Cart();
        dtoCart.setId(1L);

        cart1 = new CartDTOResponse(dtoCart);
        cart2 = new CartDTOResponse(dtoCart);


        orderList = new ArrayList<>();
        orderList.add(cart1);
        orderList.add(cart2);

        cart = new Cart();
    }

    @Test
    public void CartController_CreatesOrder() throws Exception {
        given(userService.getUser(ArgumentMatchers.any())).willReturn(userDTOResponse);
        given(cartService.createKart(ArgumentMatchers.anyLong(),
                ArgumentMatchers.any())).willReturn((CartDTOResponse) object);

        ResultActions response = mockMvc.perform(post("/orders/user/{user_id}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cart)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void CartController_GetsOrderById() throws Exception {

        given(userService.getUser(ArgumentMatchers.any())).willReturn(userDTOResponse);
        given(cartService.getKart(ArgumentMatchers.any())).willReturn(cart1);

        ResultActions response = mockMvc.perform(get("/orders/user/{user_id}/kart/{kart_id}",
                1L,1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoCart)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void CartController_GetAllOrders() throws Exception {
        given(cartService.getAllKarts()).willReturn(orderList);

        ResultActions response = mockMvc.perform(get("/orders/user/{user_id}",1L)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void CartController_UpdatesItemInOrders() throws Exception{
        given(userService.getUser(ArgumentMatchers.any())).willReturn(userDTOResponse);
        given(cartService.getKart(ArgumentMatchers.any())).willReturn(cart1);

        ResultActions response = mockMvc.perform(put("/orders/add/user/{user_id}/kart/{kart_id}",
                1L,1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoCart)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void CartController_RemovesItemInOrders() throws Exception{
        given(userService.getUser(ArgumentMatchers.any())).willReturn(userDTOResponse);
        given(cartService.getKart(ArgumentMatchers.any())).willReturn(cart1);

        ResultActions response = mockMvc.perform(put("/orders/remove/user/{user_id}/kart/{kart_id}",
                1L,1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoCart)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void CartController_CancelOrders() throws Exception{
        given(userService.getUser(ArgumentMatchers.any())).willReturn(userDTOResponse);
        given(cartService.getKart(ArgumentMatchers.any())).willReturn(cart1);

        ResultActions response = mockMvc.perform(put("/orders/cancel/user/{user_id}/kart/{kart_id}",
                1L,1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoCart)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

}
