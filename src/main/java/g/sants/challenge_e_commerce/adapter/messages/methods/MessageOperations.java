package g.sants.challenge_e_commerce.adapter.messages.methods;

public class MessageOperations {
   public static String orderCreatedQueue() {
       return MessageCategory.ORDER_QUEUE_NAME.getMessage() +
           MessageCategory.ORDER_EVENT_CREATED.getMessage();
   }

   private String orderCancelledQueue() {
       return null;
   }
}
