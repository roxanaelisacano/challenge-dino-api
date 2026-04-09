package ar.com.froneus.challenge.dino.infrastructure.dtos;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

public record DinosaurFilterDTO(
        String species,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime discoveryDateFrom,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime discoveryDateTo,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime extinctionDateFrom,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime extinctionDateTo,

        Integer limit,
        Integer offset) {

    public DinosaurFilterDTO {
        if (limit == null || limit <= 0)
            limit = 20;
        if (offset == null || offset < 0)
            offset = 0;
    }
}
