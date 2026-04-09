package ar.com.froneus.challenge.dino.domain.exceptions;

public class InvalidDinosaurStatusException extends DinosaurValidationException {
    public InvalidDinosaurStatusException(String message) {
        super(message);
    }
}
