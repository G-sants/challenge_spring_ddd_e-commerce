package g.sants.challenge_e_commerce.controllers_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import g.sants.challenge_e_commerce.application.dto.LoginDTOResponse;
import g.sants.challenge_e_commerce.application.dto.RegisterDTORequest;
import g.sants.challenge_e_commerce.application.dto.UserDTORequest;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collection;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(controllers = AuthorizationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthorizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorizationService authorizationService;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private ObjectMapper objectMapper;

    RegisterDTORequest registerDTORequest;
    LoginDTOResponse loginDTOResponse;

    @BeforeEach
    public void initUsers(){
         registerDTORequest = new RegisterDTORequest(123123123L,
                "Test1","User","test1@email.com","apassword",
                UserCategory.ADMIN);
    }

    @Test
    public void AuthorizationController_RegisterUser() throws Exception {
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
    public void AuthorizationController_LogsUserIn(){
        User mockLogin = new User(1L, "Test1","User",
                "test1@email.com","apassword");

        given(authorizationService.loadUserByUsername(ArgumentMatchers.any()))
                .willReturn(mockLogin);
    }
}
