package g.sants.challenge_e_commerce.adapter.config;

import g.sants.challenge_e_commerce.application.service.methods.MessageCategory;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MessageConfig {

    @Bean
    public Queue myQueue(){
        return new Queue(MessageCategory.ORDER_QUEUE_NAME.getMessage() + MessageCategory.ORDER_EVENT_CREATED,true);
    }

}
