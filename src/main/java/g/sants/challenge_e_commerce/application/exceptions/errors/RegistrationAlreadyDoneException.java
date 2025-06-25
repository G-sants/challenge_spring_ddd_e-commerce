package g.sants.challenge_e_commerce.application.exceptions.errors;

public class RegistrationAlreadyDoneException extends RuntimeException {

    public RegistrationAlreadyDoneException() {
        super("This email is already registered");
    }

    public RegistrationAlreadyDoneException(String msg) {
        super(msg);
    }
}
