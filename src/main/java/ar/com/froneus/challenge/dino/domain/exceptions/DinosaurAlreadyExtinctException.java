package ar.com.froneus.challenge.dino.domain.exceptions;

public class DinosaurAlreadyExtinctException extends DinosaurValidationException {
    public DinosaurAlreadyExtinctException(String message) {
        super(message);
    }
}
