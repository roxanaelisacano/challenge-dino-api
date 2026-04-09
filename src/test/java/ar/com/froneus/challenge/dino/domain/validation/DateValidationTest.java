package ar.com.froneus.challenge.dino.domain.validation;

import ar.com.froneus.challenge.dino.domain.exceptions.InvalidDinosaurDateException;
import ar.com.froneus.challenge.dino.domain.model.Dinosaur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DateValidationTest {

    private DateValidation validation;

    @BeforeEach
    void setUp() {
        validation = new DateValidation();
    }

    @Test
    void shouldThrowExceptionWhenDiscoveryDateIsNull() {
        Dinosaur dino = new Dinosaur(1L, "T-Rex", "Rex", null, null, "ALIVE");

        assertThrows(InvalidDinosaurDateException.class, () -> validation.validate(dino));
    }

    @Test
    void shouldThrowExceptionWhenDiscoveryDateIsAfterExtinctionDate() {
        LocalDateTime discovery = LocalDateTime.now();
        LocalDateTime extinction = discovery.minusDays(1);
        Dinosaur dino = new Dinosaur(1L, "T-Rex", "Rex", discovery, extinction, "EXTINCT");

        assertThrows(InvalidDinosaurDateException.class, () -> validation.validate(dino));
    }
    
    @Test
    void shouldThrowExceptionWhenDiscoveryDateIsEqualExtinctionDate() {
        LocalDateTime date = LocalDateTime.now();
        Dinosaur dino = new Dinosaur(1L, "T-Rex", "Rex", date, date, "EXTINCT");

        assertThrows(InvalidDinosaurDateException.class, () -> validation.validate(dino));
    }

    @Test
    void shouldPassWhenDatesAreValid() {
        LocalDateTime discovery = LocalDateTime.now().minusDays(100);
        LocalDateTime extinction = LocalDateTime.now();
        Dinosaur dino = new Dinosaur(1L, "T-Rex", "Rex", discovery, extinction, "EXTINCT");

        assertDoesNotThrow(() -> validation.validate(dino));
    }
}
