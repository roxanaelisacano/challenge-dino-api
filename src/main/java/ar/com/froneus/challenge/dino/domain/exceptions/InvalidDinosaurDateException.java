package ar.com.froneus.challenge.dino.domain.exceptions;

public class InvalidDinosaurDateException extends DinosaurValidationException {
    public InvalidDinosaurDateException(String message) {
        super(message);
    }
}
