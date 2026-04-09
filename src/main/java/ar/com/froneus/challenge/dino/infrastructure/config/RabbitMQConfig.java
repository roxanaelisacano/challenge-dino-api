package ar.com.froneus.challenge.dino.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
public class RabbitMQConfig {

    public static final String DINOSAUR_STATUS_QUEUE = "dino.status.queue";

    @Bean
    public Queue dinosaurStatusQueue() {
        return new Queue(DINOSAUR_STATUS_QUEUE, true);
    }

    @Bean
    public TopicExchange dinosaurExchange() {
        return new TopicExchange("dino.events");
    }

    @Bean
    public Binding binding(Queue dinosaurStatusQueue, TopicExchange dinosaurExchange) {
        return BindingBuilder.bind(dinosaurStatusQueue)
                .to(dinosaurExchange)
                .with("status.#");
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        admin.setAutoStartup(true);
        return admin;
    }
}
