package g.sants.challenge_e_commerce.repository_test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTests {

    @Test
    public void UserRepository_SavesUser(){

    }

    @Test
    public void UserRepository_FindAllUsers(){

    }

    @Test
    public void UserRepository_FindUserByEmail(){

    }

    @Test
    public void UserRepository_UpdatesUser(){

    }

    @Test
    public void UserRepository_DeleteUser(){

    }
}
