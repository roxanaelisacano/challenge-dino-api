package ar.com.froneus.challenge.dino.domain.validation;

import ar.com.froneus.challenge.dino.domain.model.Dinosaur;
import ar.com.froneus.challenge.dino.domain.exceptions.InvalidDinosaurDateException;

public class DateValidation implements DinosaurValidationStrategy {

    @Override
    public void validate(Dinosaur dinosaur) {
        if (dinosaur.getDiscoveryDate() == null) {
            throw new InvalidDinosaurDateException("La fecha de descubrimiento es requerida.");
        }
        
        if (dinosaur.getExtinctionDate() != null) {
            if (dinosaur.getDiscoveryDate().isEqual(dinosaur.getExtinctionDate()) || 
                dinosaur.getDiscoveryDate().isAfter(dinosaur.getExtinctionDate())) {
                throw new InvalidDinosaurDateException("La fecha de descubrimiento no puede ser mayor o igual a la fecha de extinción.");
            }
        }
    }
}
