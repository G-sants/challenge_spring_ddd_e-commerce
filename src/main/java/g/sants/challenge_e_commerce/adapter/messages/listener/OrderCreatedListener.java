package g.sants.challenge_e_commerce.adapter.messages.listener;

import g.sants.challenge_e_commerce.adapter.messages.methods.MessageCategory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {

    @RabbitListener(queues = MessageCategory.ORDER_CREATED)
    public void onOrderCreated(String message){
        System.out.println("Id received, " + message + " made an order.");
    }
}
