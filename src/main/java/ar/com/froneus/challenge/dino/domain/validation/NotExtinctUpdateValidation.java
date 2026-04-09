package ar.com.froneus.challenge.dino.domain.validation;

import ar.com.froneus.challenge.dino.domain.model.Dinosaur;
import ar.com.froneus.challenge.dino.domain.ports.IFindDinosaurStatusPort;
import ar.com.froneus.challenge.dino.domain.exceptions.DinosaurAlreadyExtinctException;

public class NotExtinctUpdateValidation implements DinosaurValidationStrategy {

    private final IFindDinosaurStatusPort findStatusPort;

    public NotExtinctUpdateValidation(IFindDinosaurStatusPort findStatusPort) {
        this.findStatusPort = findStatusPort;
    }

    @Override
    public void validate(Dinosaur dinosaur) {
        if (dinosaur.getId() != null) {
            String originalStatus = findStatusPort.findOriginalStatus(dinosaur.getId());
            if ("EXTINCT".equals(originalStatus)) {
                throw new DinosaurAlreadyExtinctException(
                        "No se puede modificar un dinosaurio que ya está extinto (EXTINCT).");
            }
        }
    }
}
