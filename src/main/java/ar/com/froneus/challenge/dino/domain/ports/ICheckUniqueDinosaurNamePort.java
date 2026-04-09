package ar.com.froneus.challenge.dino.domain.ports;

public interface ICheckUniqueDinosaurNamePort {
    boolean isNameTaken(String name, Long excludeId);
}
