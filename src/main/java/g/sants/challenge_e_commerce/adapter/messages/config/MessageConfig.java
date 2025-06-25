package g.sants.challenge_e_commerce.adapter.messages.config;

import g.sants.challenge_e_commerce.adapter.messages.methods.MessageCategory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MessageConfig {

    @Bean
    public Queue myQueue(){
        return new Queue(MessageCategory.ORDER_CREATED);
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> applicationReadyEventApplicationListener
            (RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }
}
