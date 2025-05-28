package g.sants.challenge_e_commerce.controllers_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import g.sants.challenge_e_commerce.application.port.input.CartController;
import g.sants.challenge_e_commerce.application.port.output.CartRepository;
import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import g.sants.challenge_e_commerce.application.service.CartService;
import g.sants.challenge_e_commerce.application.service.TokenService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

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
    private CartService cartService;

    @Autowired
    private ObjectMapper objectMapper;

}
