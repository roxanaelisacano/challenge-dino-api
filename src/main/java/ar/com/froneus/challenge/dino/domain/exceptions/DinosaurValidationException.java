package ar.com.froneus.challenge.dino.domain.exceptions;

public class DinosaurValidationException extends RuntimeException {
    public DinosaurValidationException(String message) {
        super(message);
    }
}
