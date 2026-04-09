package ar.com.froneus.challenge.dino.domain.validation;

import ar.com.froneus.challenge.dino.domain.exceptions.InvalidDinosaurStatusException;
import ar.com.froneus.challenge.dino.domain.model.Dinosaur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StatusValidationTest {

    private StatusValidation validation;

    @BeforeEach
    void setUp() {
        validation = new StatusValidation();
    }

    @Test
    void shouldThrowExceptionWhenStatusIsInvalid() {
        Dinosaur dino = new Dinosaur(1L, "T-Rex", "Rex", LocalDateTime.now(), null, "INVALID");

        assertThrows(InvalidDinosaurStatusException.class, () -> validation.validate(dino));
    }

    @Test
    void shouldThrowExceptionWhenStatusIsNull() {
        Dinosaur dino = new Dinosaur(1L, "T-Rex", "Rex", LocalDateTime.now(), null, null);

        assertThrows(InvalidDinosaurStatusException.class, () -> validation.validate(dino));
    }

    @Test
    void shouldPassWhenStatusIsValid() {
        Dinosaur dino1 = new Dinosaur(1L, "T-Rex", "Rex", LocalDateTime.now(), null, "ALIVE");
        Dinosaur dino2 = new Dinosaur(1L, "T-Rex", "Rex", LocalDateTime.now(), null, "ENDANGERED");
        Dinosaur dino3 = new Dinosaur(1L, "T-Rex", "Rex", LocalDateTime.now(), null, "EXTINCT");

        assertDoesNotThrow(() -> validation.validate(dino1));
        assertDoesNotThrow(() -> validation.validate(dino2));
        assertDoesNotThrow(() -> validation.validate(dino3));
    }
}
