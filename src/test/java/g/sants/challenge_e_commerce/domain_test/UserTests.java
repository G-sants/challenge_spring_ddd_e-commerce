package g.sants.challenge_e_commerce.domain_test;


import g.sants.challenge_e_commerce.application.dto.UserDTORequest;
import g.sants.challenge_e_commerce.application.service.methods.UserCategory;
import g.sants.challenge_e_commerce.domain.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTests {

    @Test
    void testFirstConstructor() {
        User user = new User(1L, "Test", "User",
                "test@example.com", "apass", UserCategory.USER);

        assertEquals(1L, user.getCustomerID());
        assertEquals("Test", user.getName());
        assertEquals("User", user.getLastName());
        assertEquals("test@example.com", user.getUsername());
        assertEquals("apass", user.getPassword());
    }

    @Test
    void testSecondConstructor() {
        UserDTORequest userDTO = new UserDTORequest(2L, "User",
                "Test", "test@example.com", "apass");
        User user = new User(userDTO);

        assertEquals(2L, user.getCustomerID());
        assertEquals("User", user.getName());
        assertEquals("Test", user.getLastName());
        assertEquals("test@example.com", user.getUsername());
        assertEquals("apass", user.getPassword());
    }

    @Test
    void testThirdConstructor() {
        User user = new User(3L, "User",
                "Test", "test@example.com", "apass");

        assertEquals(3L, user.getCustomerID());
        assertEquals("User", user.getName());
        assertEquals("Test", user.getLastName());
        assertEquals("test@example.com", user.getUsername());
        assertEquals("apass", user.getPassword());
    }

}