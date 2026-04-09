package ar.com.froneus.challenge.dino.domain.validation;

import ar.com.froneus.challenge.dino.domain.model.Dinosaur;
import ar.com.froneus.challenge.dino.domain.ports.ICheckUniqueDinosaurNamePort;
import ar.com.froneus.challenge.dino.domain.exceptions.DuplicatedDinosaurNameException;

public class UniqueNameValidation implements DinosaurValidationStrategy {

    private final ICheckUniqueDinosaurNamePort checkUniqueNamePort;

    public UniqueNameValidation(ICheckUniqueDinosaurNamePort checkUniqueNamePort) {
        this.checkUniqueNamePort = checkUniqueNamePort;
    }

    @Override
    public void validate(Dinosaur dinosaur) {
        if (checkUniqueNamePort.isNameTaken(dinosaur.getName(), dinosaur.getId())) {
            throw new DuplicatedDinosaurNameException("El nombre del dinosaurio '" + dinosaur.getName() + "' ya existe.");
        }
    }
}
