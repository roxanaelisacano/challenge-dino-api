package ar.com.froneus.challenge.dino.domain.validation;

import ar.com.froneus.challenge.dino.domain.model.Dinosaur;

public interface DinosaurValidationStrategy {
    void validate(Dinosaur dinosaur);
}
