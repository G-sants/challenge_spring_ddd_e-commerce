package g.sants.challenge_e_commerce.controllers_test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import g.sants.challenge_e_commerce.application.dto.RegisterDTORequest;
import g.sants.challenge_e_commerce.application.service.AuthorizationService;

import g.sants.challenge_e_commerce.application.service.TokenService;
import g.sants.challenge_e_commerce.application.service.methods.UserCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = AuthorizationControllerTest.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AuthorizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    RegisterDTORequest registerDTORequest;

    @BeforeEach
    public void initUsers(){
        RegisterDTORequest registerDTORequest = new RegisterDTORequest(123123123L,
                "Test1","User","test1@email.com","tpassword",
                UserCategory.ADMIN);
    }
    @Test
    public void AuthorizationController_RegisterUser() throws Exception {
        given(authorizationService.registerNewUser(ArgumentMatchers.any()))
                .willAnswer(InvocationOnMock::getArguments);

        ResultActions response = mockMvc.perform(post("http://localhost:8080/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDTORequest)));

        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void AuthorizationController_LogsUserIn(){

    }
}
