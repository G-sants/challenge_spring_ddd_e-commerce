package g.sants.challenge_e_commerce.controllers_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import g.sants.challenge_e_commerce.application.dto.UserDTORequest;
import g.sants.challenge_e_commerce.application.dto.UserDTOResponse;
import g.sants.challenge_e_commerce.application.port.input.UserController;
import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import g.sants.challenge_e_commerce.application.service.TokenService;
import g.sants.challenge_e_commerce.application.service.UserService;
import g.sants.challenge_e_commerce.application.service.methods.UserCategory;
import g.sants.challenge_e_commerce.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TokenService tokenService;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    List<UserDTOResponse> userList;
    UserDTOResponse userDTOResponse;
    UserDTORequest userDTORequest;
    User user;

    @BeforeEach
    void initUsers() {
        user = new User(12312312312L,"TesteUp", "UserUp",
                "test1@email.com","apassword", UserCategory.ADMIN);
        user.setId(1L);

        userDTORequest = new UserDTORequest(12312312312L,"TesteUp", "UserUp",
                "test1@email.com","apassword");

        userDTOResponse = new UserDTOResponse(1L,12312312312L,"Teste1",
                "User","test1@email.com");

        UserDTOResponse userDTOResponse1 = new UserDTOResponse(1L,12312312312L,
                "Test1", "User1","test1@email.com");
        UserDTOResponse userDTOResponse2 = new UserDTOResponse(2L,12312312312L,
                "Test2", "User2","test2@email.com");

        userList = new ArrayList<>();
        userList.add(userDTOResponse1);
        userList.add(userDTOResponse2);
    }

    @Test
    void UserController_GetsUserById() throws Exception {
        given(userService.getUser(ArgumentMatchers.any())).willReturn(userDTOResponse);

        ResultActions response = mockMvc.perform(get("/users/{userId}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTOResponse)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void UserController_GetAllUsers() throws Exception {
        given(userService.getAllUsers()).willReturn(userList);

        ResultActions response = mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void UserController_UpdatesUsers() throws Exception {

        given(userService.updateUser(1L,userDTORequest)).willReturn(user);

        ResultActions response = mockMvc.perform(put("/users/{userId}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTORequest)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void UserController_DeletesUser() throws Exception {
        doNothing().when(userService).deleteUser(ArgumentMatchers.anyLong());

        ResultActions response = mockMvc.perform(delete("/users/{userId}",1L)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
