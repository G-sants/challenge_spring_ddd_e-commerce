package g.sants.challenge_e_commerce.category_test;

import g.sants.challenge_e_commerce.adapter.messages.methods.MessageCategory;
import g.sants.challenge_e_commerce.adapter.security.methods.SecurityCategory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CategoryTests {

    static class SecurityTestsEx extends SecurityCategory {
    }

    static class MessageTestsEx extends MessageCategory {
    }

    @Test
    void testSecurityClassConstructorThrowsException() {
        Exception exception = assertThrows(IllegalStateException.class, SecurityTestsEx::new);
        assertEquals("Utility Class", exception.getMessage());
    }

    @Test
    void testMessageClassConstructorThrowsException() {
        Exception exception = assertThrows(IllegalStateException.class, MessageTestsEx::new);
        assertEquals("Utility Class", exception.getMessage());
    }
}
