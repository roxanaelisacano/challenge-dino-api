package ar.com.froneus.challenge.dino.domain.validation;

import ar.com.froneus.challenge.dino.domain.exceptions.DuplicatedDinosaurNameException;
import ar.com.froneus.challenge.dino.domain.model.Dinosaur;
import ar.com.froneus.challenge.dino.domain.ports.ICheckUniqueDinosaurNamePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UniqueNameValidationTest {

    private ICheckUniqueDinosaurNamePort portMock;
    private UniqueNameValidation validation;

    @BeforeEach
    void setUp() {
        portMock = mock(ICheckUniqueDinosaurNamePort.class);
        validation = new UniqueNameValidation(portMock);
    }

    @Test
    void shouldThrowExceptionWhenNameIsTaken() {
        Dinosaur dino = new Dinosaur(1L, "T-Rex", "Rex", LocalDateTime.now(), null, "ALIVE");
        when(portMock.isNameTaken("T-Rex", 1L)).thenReturn(true);

        assertThrows(DuplicatedDinosaurNameException.class, () -> validation.validate(dino));
    }

    @Test
    void shouldPassWhenNameIsUnique() {
        Dinosaur dino = new Dinosaur(1L, "Dino", "Rex", LocalDateTime.now(), null, "ALIVE");
        when(portMock.isNameTaken("Dino", 1L)).thenReturn(false);

        assertDoesNotThrow(() -> validation.validate(dino));
    }
}
