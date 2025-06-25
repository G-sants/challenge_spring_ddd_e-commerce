package g.sants.challenge_e_commerce.adapter.messages.methods;

public abstract class MessageCategory {

    protected MessageCategory(){
        throw  new IllegalStateException("Utility Class");
    }

    public static String concatMessage(String a, String b){
        return a + b;
    }

    public static final String ORDER_CREATED = "orders.v1.order-created";
    public static final String ORDER_CANCELLED = "orders.v1.order-cancelled";
    public static final String ORDER_PAYED = "orders.v1.order-payment-approved";

    public static final String USER_CREATED = "users.v1.user-created";

    public static final String ITEM_CREATED = "items.v1.item-created";
    public static final String ITEM_DISCOUNT = "items.v1.item-low-price";
    public static final String ITEM_OUT_OF_STOCK = "items.v1.item-stock-empty";

    public static final String CREATED_RECEIVED_ORDER = "An order was created with the following ID ";
}