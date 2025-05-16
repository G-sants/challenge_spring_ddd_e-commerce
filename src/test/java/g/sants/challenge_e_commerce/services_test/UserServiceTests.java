package g.sants.challenge_e_commerce.services_test;

import g.sants.challenge_e_commerce.application.dto.UserDTOResponse;
import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import g.sants.challenge_e_commerce.application.service.UserService;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void userService_FindAll_ReturnsAllUsers(){
        User user1 = new User(1L,"Test1","User","test1@email.com","t2password");
        User savedUser1 = userRepository.save(user1);

        User user2 = new User(2L,"Test2","User","test2@email.com","t1password");
        User savedUser2 = userRepository.save(user2);

        when(userRepository.findAll()).thenReturn(List<User>);
    }

    @Test
    public void userService_FindByEmail_ReturnsUserByEmail(){
        User user1 = new User(12312312312L,"Test1","User",
                "test1@email.com","t2password");
        UserDTOResponse userDetails = new UserDTOResponse(0L,12312312312L,
                "Test1","User","test1@email.com");

        when(userRepository.findById(anyLong())).thenReturn();

        UserDTOResponse user = userService.getUser(0L);

        Assertions.assertNotNull(user);
        Assertions.assertEquals("Test1",user.getName());
    }
}
