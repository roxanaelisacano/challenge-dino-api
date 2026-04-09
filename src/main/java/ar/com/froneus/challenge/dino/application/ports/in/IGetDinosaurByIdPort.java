package ar.com.froneus.challenge.dino.application.ports.in;

import ar.com.froneus.challenge.dino.domain.model.Dinosaur;

public interface IGetDinosaurByIdPort {

    Dinosaur execute(Long id);
}
