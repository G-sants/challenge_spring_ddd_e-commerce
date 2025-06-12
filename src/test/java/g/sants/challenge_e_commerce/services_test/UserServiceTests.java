package g.sants.challenge_e_commerce.services_test;

import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import g.sants.challenge_e_commerce.application.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void userService_FindAll_ReturnsAllUsers() {

    }

    @Test
    public void userService_FindByEmail_ReturnsUserByEmail() {

    }

    @Test
    public void userService_UpdateUser_UpdatesUserInDataBase() {

    }

    @Test
    public void userService_DeleteUser_ReturnsNull() {

    }
}