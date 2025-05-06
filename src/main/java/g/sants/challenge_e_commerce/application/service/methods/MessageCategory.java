package g.sants.challenge_e_commerce.application.service.methods;

public enum MessageCategory {

    ORDER_QUEUE_NAME("orders.v1"),

    ORDER_EVENT_CREATED(".order-created"),
    ORDER_EVENT_CANCELLED(".order-cancelled"),
    ORDER_EVENT_PAYED(".order-payment-approved"),

    USER_QUEUE_NAME("users.v1"),
    USER_EVENT_CREATED(".user-created");

    private final String message;

    MessageCategory(String message){this.message = message;}

    public String getMessage(){return message;}
}
