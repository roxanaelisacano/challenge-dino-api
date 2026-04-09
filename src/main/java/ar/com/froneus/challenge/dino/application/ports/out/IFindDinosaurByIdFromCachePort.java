package ar.com.froneus.challenge.dino.application.ports.out;

import java.util.Optional;

import ar.com.froneus.challenge.dino.domain.model.Dinosaur;

public interface IFindDinosaurByIdFromCachePort {

    Optional<Dinosaur> findById(Long id);
}
