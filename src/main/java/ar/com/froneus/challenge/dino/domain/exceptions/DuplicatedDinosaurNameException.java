package ar.com.froneus.challenge.dino.domain.exceptions;

public class DuplicatedDinosaurNameException extends DinosaurValidationException {
    public DuplicatedDinosaurNameException(String message) {
        super(message);
    }
}
