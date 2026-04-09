package ar.com.froneus.challenge.dino.domain.validation;

import ar.com.froneus.challenge.dino.domain.exceptions.DinosaurAlreadyExtinctException;
import ar.com.froneus.challenge.dino.domain.model.Dinosaur;
import ar.com.froneus.challenge.dino.domain.ports.IFindDinosaurStatusPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class NotExtinctUpdateValidationTest {

    private IFindDinosaurStatusPort portMock;
    private NotExtinctUpdateValidation validation;

    @BeforeEach
    void setUp() {
        portMock = mock(IFindDinosaurStatusPort.class);
        validation = new NotExtinctUpdateValidation(portMock);
    }

    @Test
    void shouldThrowExceptionWhenDinoWasAlreadyExtinct() {
        Dinosaur dino = new Dinosaur(1L, "T-Rex", "Rex", LocalDateTime.now(), null, "ALIVE");
        when(portMock.findOriginalStatus(1L)).thenReturn("EXTINCT");

        assertThrows(DinosaurAlreadyExtinctException.class, () -> validation.validate(dino));
    }

    @Test
    void shouldPassWhenDinoWasNotExtinct() {
        Dinosaur dino = new Dinosaur(1L, "T-Rex", "Rex", LocalDateTime.now(), null, "ALIVE");
        when(portMock.findOriginalStatus(1L)).thenReturn("ALIVE");

        assertDoesNotThrow(() -> validation.validate(dino));
    }

    @Test
    void shouldPassWhenCreatingNewDinosaur() {
        Dinosaur dino = new Dinosaur(null, "T-Rex", "Rex", LocalDateTime.now(), null, "ALIVE");

        assertDoesNotThrow(() -> validation.validate(dino));
        verifyNoInteractions(portMock);
    }
}
