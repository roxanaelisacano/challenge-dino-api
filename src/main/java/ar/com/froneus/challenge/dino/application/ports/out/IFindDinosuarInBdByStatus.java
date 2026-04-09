package ar.com.froneus.challenge.dino.application.ports.out;

import java.time.LocalDateTime;
import java.util.List;

import ar.com.froneus.challenge.dino.domain.model.Dinosaur;

public interface IFindDinosuarInBdByStatus {
    List<Dinosaur> findAliveToBecomeEndangered(LocalDateTime limit);

    List<Dinosaur> findToBecomeExtinct(LocalDateTime now);
}
