package ar.com.froneus.challenge.dino.application.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.com.froneus.challenge.dino.application.ports.out.IEvictDinosaurFromCachePort;
import ar.com.froneus.challenge.dino.application.ports.out.IFindDinosaurByIdPort;
import ar.com.froneus.challenge.dino.application.ports.out.IUpdateDinosaurInDbPort;
import ar.com.froneus.challenge.dino.domain.model.Dinosaur;
import ar.com.froneus.challenge.dino.domain.validation.DinosaurValidationStrategy;

public class UpdateDinosaurUseCaseTest {

    private IUpdateDinosaurInDbPort updateDinosaurInDbPortMock;
    private IFindDinosaurByIdPort findDinosaurByIdPortMock;
    private IEvictDinosaurFromCachePort evictPortMock;
    private DinosaurValidationStrategy validationMock;
    private UpdateDinosaurUseCase useCase;

    private Long dinosaurId;
    private Dinosaur existingDino;
    private Dinosaur updatedDinoData;

    @BeforeEach
    void setUp() {
        updateDinosaurInDbPortMock = mock(IUpdateDinosaurInDbPort.class);
        findDinosaurByIdPortMock = mock(IFindDinosaurByIdPort.class);
        evictPortMock = mock(IEvictDinosaurFromCachePort.class);
        validationMock = mock(DinosaurValidationStrategy.class);

        useCase = new UpdateDinosaurUseCase(
                findDinosaurByIdPortMock,
                updateDinosaurInDbPortMock,
                evictPortMock,
                List.of(validationMock));

        dinosaurId = 1L;
        existingDino = new Dinosaur(dinosaurId, "T-Rex", "Rex", LocalDateTime.now(), null, "ALIVE");
        updatedDinoData = new Dinosaur(null, "Tyrannosaurus Rex", "Rex", LocalDateTime.now(), null, "ALIVE");
    }

    @Test
    void shouldUpdateDinosaurWhenValidationPasses() {
        Dinosaur resultDino = new Dinosaur(
                dinosaurId,
                "Tyrannosaurus Rex",
                "Rex",
                updatedDinoData.getDiscoveryDate(),
                null,
                "ALIVE");

        doNothing().when(validationMock).validate(existingDino);

        when(findDinosaurByIdPortMock.findById(dinosaurId)).thenReturn(Optional.of(existingDino));
        when(updateDinosaurInDbPortMock.update(any())).thenReturn(resultDino);

        Dinosaur result = useCase.execute(dinosaurId, updatedDinoData);

        assertEquals(dinosaurId, result.getId());
        assertEquals("Tyrannosaurus Rex", result.getName());

        verify(findDinosaurByIdPortMock).findById(dinosaurId);
        verify(updateDinosaurInDbPortMock).update(any());

        verify(evictPortMock).evict(any());
    }
}
