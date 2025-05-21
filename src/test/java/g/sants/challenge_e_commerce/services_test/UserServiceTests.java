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
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
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
        User user1 = new User(12312312312L, "Test1", "User",
                "test1@email.com", "t2Password", UserCategory.ADMIN);

        when(userRepository.save(any(User.class))).thenAnswer(arg -> {
            User savedUser1 = arg.getArgument(0);
            savedUser1.setId(1L);
            return savedUser1;
        });

        userRepository.save(user1);

        User user2 = new User(12312312312L, "Test2", "User",
                "test2@email.com", "t2Password", UserCategory.ADMIN);

        when(userRepository.save(any(User.class))).thenAnswer(arg -> {
            User savedUser2 = arg.getArgument(0);
            savedUser2.setId(1L);
            return savedUser2;
        });

        userRepository.save(user2);

        when(userRepository.findAll()).thenReturn(List.of(user1,user2));

        List<UserDTOResponse> userList = userService.getAllUsers();

        Assertions.assertNotNull(userList);
        Assertions.assertEquals(2,userList.size());
    }

    @Test
    public void userService_FindByEmail_ReturnsUserByEmail(){
        User user = new User(12312312312L, "Test1", "User",
                "test1@email.com", "t2Password", UserCategory.ADMIN);

        when(userRepository.save(any(User.class))).thenAnswer(arg -> {
            User savedUser  = arg.getArgument(0);
            savedUser .setId(1L);
            return savedUser;
        });

        userRepository.save(user);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDTOResponse userResponse = userService.getUser(1L);

        Assertions.assertNotNull(user );
        Assertions.assertEquals("Test1",userResponse.getName());
        Assertions.assertEquals("User", userResponse.getLastName());
        Assertions.assertEquals("test1@email.com",userResponse.getEmail());
    }

    @Test
    public void userService_UpdateUser_UpdatesUserInDataBase(){
        User user = new User(12312312312L, "Test1", "User",
                "test1@email.com", "t2Password", UserCategory.ADMIN);

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
    public void userService_DeleteUser_ReturnsNull(){
        User user = new User(12312312312L, "Test1", "User",
                "test1@email.com", "t2Password", UserCategory.ADMIN);

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
