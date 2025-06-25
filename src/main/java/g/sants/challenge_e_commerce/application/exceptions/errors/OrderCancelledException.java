package g.sants.challenge_e_commerce.application.exceptions.errors;

public class OrderCancelledException extends RuntimeException {

    public OrderCancelledException() {
        super("This Order is Cancelled");
    }

    public OrderCancelledException(String msg) {
        super(msg);
    }
}
