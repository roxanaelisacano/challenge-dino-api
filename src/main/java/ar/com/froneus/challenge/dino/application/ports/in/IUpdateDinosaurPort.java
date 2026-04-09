package ar.com.froneus.challenge.dino.application.ports.in;

import ar.com.froneus.challenge.dino.domain.model.Dinosaur;

public interface IUpdateDinosaurPort {

    Dinosaur execute(Long id, Dinosaur dinosaur);
}
