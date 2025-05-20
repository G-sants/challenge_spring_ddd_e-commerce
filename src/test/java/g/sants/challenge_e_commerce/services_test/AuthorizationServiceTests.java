package g.sants.challenge_e_commerce.services_test;

import g.sants.challenge_e_commerce.application.dto.RegisterDTORequest;
import g.sants.challenge_e_commerce.application.dto.UserDTOResponse;
import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import g.sants.challenge_e_commerce.application.service.AuthorizationService;
import g.sants.challenge_e_commerce.application.service.methods.UserCategory;
import g.sants.challenge_e_commerce.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorizationServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthorizationService authorizationService;

    public AuthorizationServiceTests(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void AuthorizationService_RegisterUser(){
        RegisterDTORequest userRegistration = new RegisterDTORequest(12312312312L,
                "Test1", "User","test1@email.com",
                "tpassword", UserCategory.ADMIN);

        when(userRepository.findByEmail(userRegistration.email())).thenReturn(null);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        UserDTOResponse responseUser = authorizationService.registerNewUser(userRegistration);

        Assertions.assertNotNull(responseUser);
        Assertions.assertEquals("Test1", responseUser.getName());
        Assertions.assertEquals("User", responseUser.getLastName());
        Assertions.assertEquals("test1@email.com", responseUser.getEmail());
        Assertions.assertEquals(1L, responseUser.getId());
    }

    @Test
    public void AuthorizationService_LogsInUser(){
        RegisterDTORequest userRegistration = new RegisterDTORequest(12312312312L,
                "Test1", "User ", "test1@email.com",
                "tpassword", UserCategory.ADMIN);

        User mockUser = new User(123123123L,
                userRegistration.name(), userRegistration.lastName(),
                userRegistration.email(),userRegistration.password(), userRegistration.category());
        mockUser.setId(1L);

        when(userRepository.findByEmail(userRegistration.email())).thenReturn(mockUser);
        UserDTOResponse responseUser  = authorizationService.registerNewUser (userRegistration);

        UserDetails userDetails = authorizationService.loadUserByUsername(responseUser.getEmail());

        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(userRegistration.email(), userDetails.getUsername());
    }


}
