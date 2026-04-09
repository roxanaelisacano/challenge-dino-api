package ar.com.froneus.challenge.dino.domain.exception;

public class DinosaurNotFoundException extends RuntimeException {

    public DinosaurNotFoundException(Long id) {
        super("Dinosaur with id " + id + " not found");
    }
}
