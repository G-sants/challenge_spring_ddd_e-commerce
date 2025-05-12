package g.sants.challenge_e_commerce.application.exceptions.errors;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException() {
        super("Order not Found");
    }

    public OrderNotFoundException(String msg) {
        super(msg);
    }
}
