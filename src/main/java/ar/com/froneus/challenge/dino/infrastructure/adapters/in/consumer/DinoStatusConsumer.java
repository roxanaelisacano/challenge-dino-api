package ar.com.froneus.challenge.dino.infrastructure.adapters.in.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class DinoStatusConsumer {

    @RabbitListener(queues = "dino.status.queue")
    public void handleStatusChange(Map<String, Object> message) {
        log.info("[RabbitMQ] Mensaje recibido desde la cola 'dino.status.queue'");

        Object id = message.get("dinosaurId");
        Object status = message.get("newStatus");
        Object timestamp = message.get("timestamp");

        log.info("CAMBIO DE ESTADO  - ID: {} | Nuevo Estado: {} | Fecha Evento: {}",
                id, status, timestamp);
    }
}
