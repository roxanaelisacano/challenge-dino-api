package ar.com.froneus.challenge.dino.application.useCases;

import ar.com.froneus.challenge.dino.application.ports.out.IEvictDinosaurFromCachePort;
import ar.com.froneus.challenge.dino.application.ports.out.ISaveDinosaurPort;
import ar.com.froneus.challenge.dino.domain.model.Dinosaur;
import ar.com.froneus.challenge.dino.domain.validation.DinosaurValidationStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CreateDinosaurUseCaseTest {

    private ISaveDinosaurPort savePortMock;
    private IEvictDinosaurFromCachePort evictPortMock;
    private DinosaurValidationStrategy validationMock;
    private CreateDinosaurUseCase useCase;

    @BeforeEach
    void setUp() {
        savePortMock = mock(ISaveDinosaurPort.class);
        evictPortMock = mock(IEvictDinosaurFromCachePort.class);
        validationMock = mock(DinosaurValidationStrategy.class);
        useCase = new CreateDinosaurUseCase(savePortMock, evictPortMock, List.of(validationMock));
    }

    @Test
    void shouldSaveDinosaurWhenValidationPasses() {
        Dinosaur dino = new Dinosaur(null, "T-Rex", "Rex", LocalDateTime.now(), null, "ALIVE");
        Dinosaur savedDino = new Dinosaur(1L, "T-Rex", "Rex", dino.getDiscoveryDate(), null, "ALIVE");

        doNothing().when(validationMock).validate(dino);
        when(savePortMock.save(dino)).thenReturn(savedDino);

        Dinosaur result = useCase.execute(dino);

        assertEquals(1L, result.getId());
        verify(validationMock).validate(dino);
        verify(savePortMock).save(dino);
        verify(evictPortMock).evictLists();
    }

    @Test
    void shouldNotSaveDinosaurWhenValidationFails() {
        Dinosaur dino = new Dinosaur(null, "T-Rex", "Rex", LocalDateTime.now(), null, "ALIVE");

        doThrow(new RuntimeException("Validation failed")).when(validationMock).validate(dino);

        assertThrows(RuntimeException.class, () -> useCase.execute(dino));

        verify(validationMock).validate(dino);
        verify(savePortMock, never()).save(any());
    }
}
