package g.sants.challenge_e_commerce.controllers_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import g.sants.challenge_e_commerce.application.dto.AuthorizationDTORequest;
import g.sants.challenge_e_commerce.application.dto.RegisterDTORequest;
import g.sants.challenge_e_commerce.application.dto.UserDTOResponse;
import g.sants.challenge_e_commerce.application.port.input.AuthorizationController;
import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import g.sants.challenge_e_commerce.application.service.AuthorizationService;

import g.sants.challenge_e_commerce.application.service.TokenService;
import g.sants.challenge_e_commerce.application.service.methods.UserCategory;
import g.sants.challenge_e_commerce.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(controllers = AuthorizationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthorizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthorizationService authorizationService;

    @MockitoBean
    private TokenService tokenService;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private ObjectMapper objectMapper;

    RegisterDTORequest registerDTORequest;
    AuthorizationDTORequest authorizationDTORequest;

    @BeforeEach
    void initUsers(){

        MockitoAnnotations.openMocks(this);

         registerDTORequest = new RegisterDTORequest(123123123L,
                "Test1","User","test1@email.com","apassword",
                UserCategory.ADMIN);

         authorizationDTORequest = new AuthorizationDTORequest("test1@email.com",
                 "apassword");

        User user = new User(123123123L, "Test1","User",
                "test1@email.com","apassword");

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);

        when(authenticationManager.authenticate(ArgumentMatchers.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
    }

    @Test
    void AuthorizationController_RegisterUser() throws Exception {
        UserDTOResponse mockUser = new UserDTOResponse(1L,123123123L,
                "Test1","User","test1@email.com");
        given(authorizationService.registerNewUser(ArgumentMatchers.any()))
                .willReturn(mockUser);

        ResultActions response = mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDTORequest)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void AuthorizationController_LogsUserIn() throws Exception {
        String token = "tokenloginresponsetest123";

        given(tokenService.generateToken(ArgumentMatchers.any()))
                .willReturn(token);

        ResultActions response = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authorizationDTORequest)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
