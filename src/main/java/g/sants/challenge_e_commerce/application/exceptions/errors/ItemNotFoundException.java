package g.sants.challenge_e_commerce.application.exceptions.errors;

public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException() {
        super("Item not in Stock");
    }

    public ItemNotFoundException(String msg) {
        super(msg);
    }
}
