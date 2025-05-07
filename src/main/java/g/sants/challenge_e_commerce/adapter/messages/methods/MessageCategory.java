package g.sants.challenge_e_commerce.adapter.messages.methods;

public enum MessageCategory {

    ORDER_QUEUE_NAME("orders.v1"),

    ORDER_EVENT_CREATED(".order-created"),
    ORDER_EVENT_CANCELLED(".order-cancelled"),
    ORDER_EVENT_PAYED(".order-payment-approved"),

    USER_QUEUE_NAME("users.v1"),

    USER_EVENT_CREATED(".user-created"),

    ITEM_QUEUE_NAME("item.v1"),

    ITEM_EVENT_CREATED(".item-created"),
    ITEM_EVENT_DISCOUNT(".item-low-price"),
    ITEM_EVENT_OUTOFSTOCK(".item-stockempty");

    private final String message;

    MessageCategory(String message){this.message = message;}

    public String getMessage(){return message;}
}
