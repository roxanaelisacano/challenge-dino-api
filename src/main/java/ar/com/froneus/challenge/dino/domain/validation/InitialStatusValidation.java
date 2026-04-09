package ar.com.froneus.challenge.dino.domain.validation;

import ar.com.froneus.challenge.dino.domain.model.Dinosaur;
import ar.com.froneus.challenge.dino.domain.exceptions.InvalidDinosaurStatusException;

public class InitialStatusValidation implements DinosaurValidationStrategy {

    @Override
    public void validate(Dinosaur dinosaur) {
        // Asumimos que si el ID es nulo, es una creación nueva
        if (dinosaur.getId() == null) {
            if (!"ALIVE".equals(dinosaur.getStatus())) {
                throw new InvalidDinosaurStatusException("El status inicial al crear un dinosaurio debe ser obligatoriamente ALIVE.");
            }
        }
    }
}
