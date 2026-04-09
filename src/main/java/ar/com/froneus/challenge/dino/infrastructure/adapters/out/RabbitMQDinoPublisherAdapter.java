package ar.com.froneus.challenge.dino.infrastructure.adapters.out;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import ar.com.froneus.challenge.dino.application.ports.out.IDinoMessagePublisherPort;

@Component
public class RabbitMQDinoPublisherAdapter implements IDinoMessagePublisherPort {
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQDinoPublisherAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishStatusChange(Long id, String newStatus, LocalDateTime timestamp) {
        var message = Map.of(
                "dinosaurId", id,
                "newStatus", newStatus,
                "timestamp", timestamp.toString());
        rabbitTemplate.convertAndSend("dino.events", "status.key", message);
    }
}
