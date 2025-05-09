package g.sants.challenge_e_commerce.application.exceptions.errors;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
