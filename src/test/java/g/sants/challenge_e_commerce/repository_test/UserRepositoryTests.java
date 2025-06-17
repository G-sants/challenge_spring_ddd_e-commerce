package g.sants.challenge_e_commerce.repository_test;

import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import g.sants.challenge_e_commerce.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void UserRepository_SavesUser(){
        User user = new User(1L,"Test",
                "User","test@email.com","tpassword");
        User savedUser = userRepository.save(user);

        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals("test@email.com", savedUser.getUsername());
    }

    @Test
    void UserRepository_FindAllUsers(){
        User user1 = new User(1L,"Test1","User","test1@email.com","t2password");
        User savedUser1 = userRepository.save(user1);

        User user2 = new User(2L,"Test2","User","test2@email.com","t1password");
        User savedUser2 = userRepository.save(user2);

        List<User> usersList = userRepository.findAll();

        Assertions.assertNotNull(usersList);
        Assertions.assertEquals(2,usersList.size());
    }

    @Test
    void UserRepository_FindUserByEmail(){
        User user1 = new User(1L,"Test1","User","test1@email.com","t2password");
        User savedUser1 = userRepository.save(user1);

        User idUser = (User) userRepository.findByEmail("test1@email.com");

        Assertions.assertEquals(savedUser1.getUsername(),idUser.getUsername());
    }

    @Test
    void UserRepository_UpdatesUser(){
        User user1 = new User(1L,"Test1","User","test1@email.com","t2password");
        User savedUser1 = userRepository.save(user1);

        User idUser = (User) userRepository.findByEmail("test1@email.com");
        idUser.setName("1Test");
        idUser.setLastName("Update User");
        userRepository.save(idUser);

        Assertions.assertEquals("1Test",idUser.getName());
        Assertions.assertEquals("Update User",idUser.getLastName());
    }

    @Test
    void UserRepository_DeleteUser(){
        User user1 = new User(1L,"Test1","User","test1@email.com","t2password");
        User savedUser1 = userRepository.save(user1);

        userRepository.deleteById(savedUser1.getId());

        Assertions.assertNull(userRepository.findByEmail("test1@email.com"));
    }
}
