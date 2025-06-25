package g.sants.challenge_e_commerce.adapter.messages.listener;

import g.sants.challenge_e_commerce.adapter.messages.methods.MessageCategory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class OrderCreatedListener {

    Logger logger = Logger.getLogger(getClass().getName());

    @RabbitListener(queues = MessageCategory.ORDER_CREATED)
    public void onOrderCreated(String message){
        if (logger.isLoggable(Level.INFO)) {
            logger.info(() -> MessageCategory.concatMessage(MessageCategory.CREATED_RECEIVED_ORDER, message));
        }
    }
}
