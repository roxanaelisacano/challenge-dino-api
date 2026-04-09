package ar.com.froneus.challenge.dino.application.ports.out;

import java.time.LocalDateTime;

public interface IDinoMessagePublisherPort {
    void publishStatusChange(Long id, String newStatus, LocalDateTime timestamp);
}