package g.sants.challenge_e_commerce.services_test;

import g.sants.challenge_e_commerce.application.dto.UserDTORequest;
import g.sants.challenge_e_commerce.application.dto.UserDTOResponse;
import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import g.sants.challenge_e_commerce.application.service.UserService;
import g.sants.challenge_e_commerce.application.service.methods.UserCategory;
import g.sants.challenge_e_commerce.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setup() {
        user = new User(12312312312L,"Test","User",
                "test@email.com","tpassword", UserCategory.ADMIN);
        user.setId(1L);
    }

    @Test
    void userService_FindAll_ReturnsAllUsers() {
        User user1 = new User(12312312312L, "Test1", "User",
                "test1@email.com", "t2Password", UserCategory.ADMIN);
        user1.setId(1L);

        User user2 = new User(12312312312L, "Test2", "User",
                "test2@email.com", "t2Password", UserCategory.ADMIN);
        user2.setId(2L);

        when(userRepository.findAll()).thenReturn(List.of(user1,user2));

        List<UserDTOResponse> users = userService.getAllUsers();
        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals("test1@email.com", users.get(0).getEmail());
        assertEquals("test2@email.com", users.get(1).getEmail());
    }

    @Test
    void userService_FindByID_ReturnsUserByID() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));

        UserDTOResponse response = userService.getUser(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("test@email.com", response.getEmail());
        verify(userRepository).findById(1L);
    }

    @Test
    void userService_UpdateUser_UpdatesUserInDataBase() {
        when(userRepository.save(any(User.class))).thenAnswer(arg -> {
            User savedUser  = arg.getArgument(0);
            savedUser .setId(1L);
            return savedUser;
        });
        userRepository.save(user);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDTORequest userDTORequest = new UserDTORequest(12312312312L,"TestUp",
                "UserUp","test1@email.com","t2password");
        userService.updateUser(1L,userDTORequest);

        Assertions.assertEquals("TestUp", userRepository.findById(1L).get().getName());
    }

    @Test
    void userService_DeleteUser_ReturnsNull() {
        List<User> userList = new ArrayList<>();

        when(userRepository.save(any(User.class))).thenAnswer(arg -> {
            User savedUser  = arg.getArgument(0);
            savedUser .setId(1L);
            userList.add(savedUser);
            return savedUser;
        });

        when(userRepository.findById(1L)).thenAnswer(arg ->
                userList.stream().filter(u -> u.getId()==1L).findFirst());
        userRepository.save(user);

        userRepository.deleteById(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertFalse(userRepository.findById(1L).isPresent());
    }
}