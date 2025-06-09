package g.sants.challenge_e_commerce.services_test;

import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import g.sants.challenge_e_commerce.application.service.AuthorizationService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthorizationServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthorizationService authorizationService;

    public AuthorizationServiceTests() {
        MockitoAnnotations.openMocks(this);
    }
}
/*    @Test
    public void AuthorizationService_RegisterUser(){
        RegisterDTORequest userRegistration = new RegisterDTORequest(12312312312L,
                "Test1", "User","test1@email.com",
                "tpassword", UserCategory.ADMIN);

        when(userRepository.findByEmail(userRegistration.email())).thenReturn(null);

        when(userRepository.save(any(User.class))).thenAnswer(arg -> {
            User user = arg.getArgument(0);
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

        User mockUser = new User(12312312312L,
                userRegistration.name(), userRegistration.lastName(),
                userRegistration.email(),userRegistration.password(), userRegistration.category());
        mockUser.setId(1L);

        when(userRepository.findByEmail(userRegistration.email())).thenReturn(mockUser);

        UserDetails userDetails = authorizationService.loadUserByUsername(userRegistration.email());

        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(userRegistration.password(),userDetails.getPassword());
    }
}
*/