package ar.com.froneus.challenge.dino.infrastructure.dtos;

import java.time.LocalDateTime;

public record DinosaurResponseDTO(Long id, String name, String species, LocalDateTime discoveryDate,
        LocalDateTime extinctionDate,
        String status) {

}
