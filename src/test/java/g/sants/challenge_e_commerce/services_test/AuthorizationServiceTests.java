package g.sants.challenge_e_commerce.services_test;

import g.sants.challenge_e_commerce.application.dto.RegisterDTORequest;
import g.sants.challenge_e_commerce.application.dto.UserDTOResponse;
import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import g.sants.challenge_e_commerce.application.service.AuthorizationService;
import g.sants.challenge_e_commerce.application.service.methods.UserCategory;
import g.sants.challenge_e_commerce.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthorizationService authorizationService;

    private User user;
    private RegisterDTORequest register;

    @BeforeEach
    void setup(){
        user = new User(12312312312L,"Test","User",
                "test@email.com","tpassword", UserCategory.ADMIN);
        user.setId(1L);

        register = new RegisterDTORequest(123123123L, "Test", "User",
                "test@email.com", "tpassword", UserCategory.ADMIN);
    }

    @Test
    void AuthorizationService_RegisterUser(){
        when(userRepository.findByEmail("test@email.com")).thenReturn(user);

        UserDetails userDetails = authorizationService.loadUserByUsername("test@email.com");

        assertNotNull(userDetails);
        assertEquals("test@email.com", userDetails.getUsername());
        verify(userRepository, times(1)).findByEmail("test@email.com");
    }

    @Test
    void AuthorizationService_LogsInUser(){
        when(userRepository.findByEmail("test@email.com")).thenReturn(null);
        when(userRepository.save(any(User.class))).thenAnswer(obj -> {
            User savedUser  = obj.getArgument(0);
            savedUser .setId(1L);
            return savedUser ;
        });

        UserDTOResponse response = authorizationService.registerNewUser (register);

        assertNotNull(response);
        assertEquals("test@email.com", response.getEmail());
        verify(userRepository, times(1)).findByEmail("test@email.com");
        verify(userRepository, times(1)).save(any(User.class));
    }
}