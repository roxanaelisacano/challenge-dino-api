package ar.com.froneus.challenge.dino.domain.validation;

import ar.com.froneus.challenge.dino.domain.model.Dinosaur;
import ar.com.froneus.challenge.dino.domain.exceptions.InvalidDinosaurStatusException;

public class StatusValidation implements DinosaurValidationStrategy {

    @Override
    public void validate(Dinosaur dinosaur) {
        String status = dinosaur.getStatus();
        if (status == null || (!status.equals("ALIVE") && !status.equals("ENDANGERED") && !status.equals("EXTINCT"))) {
            throw new InvalidDinosaurStatusException("El status debe ser ALIVE, ENDANGERED o EXTINCT.");
        }
    }
}
