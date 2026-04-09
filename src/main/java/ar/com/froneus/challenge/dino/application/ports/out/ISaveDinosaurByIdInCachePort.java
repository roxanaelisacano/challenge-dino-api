package ar.com.froneus.challenge.dino.application.ports.out;

import ar.com.froneus.challenge.dino.domain.model.Dinosaur;

public interface ISaveDinosaurByIdInCachePort {

    void save(Long id, Dinosaur dinosaur);
}
