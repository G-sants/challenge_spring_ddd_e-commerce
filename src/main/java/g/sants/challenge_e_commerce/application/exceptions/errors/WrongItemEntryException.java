package g.sants.challenge_e_commerce.application.exceptions.errors;

public class WrongItemEntryException extends RuntimeException {

    public WrongItemEntryException() {
        super("Wrong Item Entry");
    }

    public WrongItemEntryException(String msg) {
        super(msg);
    }
}
