package ar.com.froneus.challenge.dino.infrastructure.dtos;

import java.time.LocalDateTime;

public record DinosaurRequestDTO(String name, String species, LocalDateTime discoveryDate,
                LocalDateTime extinctionDate,
                String status) {

}
