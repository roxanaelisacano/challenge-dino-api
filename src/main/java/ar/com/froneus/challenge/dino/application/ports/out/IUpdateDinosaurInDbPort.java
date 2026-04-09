package ar.com.froneus.challenge.dino.application.ports.out;

import ar.com.froneus.challenge.dino.domain.model.Dinosaur;

public interface IUpdateDinosaurInDbPort {

    Dinosaur update(Dinosaur dinosaur);
}
