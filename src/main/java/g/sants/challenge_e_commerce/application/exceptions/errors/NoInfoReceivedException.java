package g.sants.challenge_e_commerce.application.exceptions.errors;

public class NoInfoReceivedException extends RuntimeException {

    public NoInfoReceivedException() {
        super("No information received from the Service");
    }

    public NoInfoReceivedException(String msg) {
        super(msg);
    }
}
