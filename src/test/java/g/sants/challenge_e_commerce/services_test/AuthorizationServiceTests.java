package g.sants.challenge_e_commerce.services_test;

import g.sants.challenge_e_commerce.application.dto.RegisterDTORequest;
import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import g.sants.challenge_e_commerce.application.service.AuthorizationService;
import g.sants.challenge_e_commerce.application.service.methods.UserCategory;
import g.sants.challenge_e_commerce.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;


import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorizationServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthorizationService authorizationService;

    @Test
    public void AuthorizationService_RegisterUser(){
        User user = new User(1L,"Test",
                "User","test@email.com","tpassword");

        RegisterDTORequest userRegistration = new RegisterDTORequest(12312312312L,
                "Test", "User","test@email.com",
                "tpassword", UserCategory.ADMIN);

        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        ResponseEntity savedUser = authorizationService.registerNewUser(userRegistration);

        Assertions.assertThat(savedUser).isNotNull();

        //ERROR IN THEST SAVEDUSER IS NOT FOUND
    }

    @Test
    public void AuthorizationService_LogsInUser(){

    }


}
