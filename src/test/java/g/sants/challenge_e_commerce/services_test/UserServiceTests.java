package g.sants.challenge_e_commerce.services_test;

import g.sants.challenge_e_commerce.application.dto.UserDTORequest;
import g.sants.challenge_e_commerce.application.dto.UserDTOResponse;
import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import g.sants.challenge_e_commerce.application.service.UserService;
import g.sants.challenge_e_commerce.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void UserService_CreateUser_ReturnUserDTO(){
        User user = new User(1L,"Test",
                "User","test@email.com","tpassword");
        UserDTORequest userDTORequest = new UserDTORequest(123123123L,"User",
                "Test","test@email.com","tpassword");

        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        UserDTORequest savedUser = userService.save(userDTORequest);

        Assertions.assertThat(savedUser).isNotNull();
    }


}
