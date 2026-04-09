package ar.com.froneus.challenge.dino.domain.validation;

import ar.com.froneus.challenge.dino.domain.exceptions.InvalidDinosaurStatusException;
import ar.com.froneus.challenge.dino.domain.model.Dinosaur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InitialStatusValidationTest {

    private InitialStatusValidation validation;

    @BeforeEach
    void setUp() {
        validation = new InitialStatusValidation();
    }

    @Test
    void shouldThrowExceptionWhenNewDinosaurIsNotAlive() {
        Dinosaur dino = new Dinosaur(null, "T-Rex", "Rex", LocalDateTime.now(), null, "EXTINCT");

        assertThrows(InvalidDinosaurStatusException.class, () -> validation.validate(dino));
    }

    @Test
    void shouldPassWhenNewDinosaurIsAlive() {
        Dinosaur dino = new Dinosaur(null, "T-Rex", "Rex", LocalDateTime.now(), null, "ALIVE");

        assertDoesNotThrow(() -> validation.validate(dino));
    }

    @Test
    void shouldPassWhenUpdatingDinosaur() {
        Dinosaur dino = new Dinosaur(1L, "T-Rex", "Rex", LocalDateTime.now(), null, "EXTINCT");

        assertDoesNotThrow(() -> validation.validate(dino));
    }
}
