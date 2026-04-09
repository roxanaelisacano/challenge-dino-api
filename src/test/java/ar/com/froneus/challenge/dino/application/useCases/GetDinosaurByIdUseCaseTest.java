package ar.com.froneus.challenge.dino.application.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.com.froneus.challenge.dino.application.ports.out.IFindDinosaurByIdFromCachePort;
import ar.com.froneus.challenge.dino.application.ports.out.IFindDinosaurByIdPort;
import ar.com.froneus.challenge.dino.application.ports.out.ISaveDinosaurByIdInCachePort;
import ar.com.froneus.challenge.dino.domain.model.Dinosaur;

public class GetDinosaurByIdUseCaseTest {

    private IFindDinosaurByIdPort getDinosaurByIdPortMock;
    private IFindDinosaurByIdFromCachePort findDinosaurInCachePortMock;
    private ISaveDinosaurByIdInCachePort saveDinosaurInCachePortMock;
    private GetDinosaurByIdUseCase useCase;

    private Long dinosaurId;
    private Dinosaur expectedDino;

    @BeforeEach
    void setUp() {
        getDinosaurByIdPortMock = mock(IFindDinosaurByIdPort.class);
        findDinosaurInCachePortMock = mock(IFindDinosaurByIdFromCachePort.class);
        saveDinosaurInCachePortMock = mock(ISaveDinosaurByIdInCachePort.class);

        useCase = new GetDinosaurByIdUseCase(
                getDinosaurByIdPortMock,
                findDinosaurInCachePortMock,
                saveDinosaurInCachePortMock);

        dinosaurId = 1L;
        expectedDino = new Dinosaur(dinosaurId, "T-Rex", "Rex", LocalDateTime.now(), null, "ALIVE");
    }

    @Test
    void executeShouldReturnDinosaurFromDbAndSaveInCacheWhenNoExistInCache() {
        when(findDinosaurInCachePortMock.findById(dinosaurId)).thenReturn(Optional.empty());
        when(getDinosaurByIdPortMock.findById(dinosaurId)).thenReturn(Optional.of(expectedDino));

        Dinosaur result = useCase.execute(dinosaurId);
        assertNotNull(result);
        assertEquals(expectedDino.getId(), result.getId());
        assertEquals(expectedDino.getName(), result.getName());

        verify(findDinosaurInCachePortMock).findById(dinosaurId);
        verify(getDinosaurByIdPortMock).findById(dinosaurId);
        verify(saveDinosaurInCachePortMock).save(any(), any());
    }

    @Test
    void executeShouldReturnDinosaurFromCacheDirectlyWhenExists() {
        when(findDinosaurInCachePortMock.findById(dinosaurId)).thenReturn(Optional.of(expectedDino));

        Dinosaur result = useCase.execute(dinosaurId);

        assertNotNull(result);
        assertEquals(expectedDino.getName(), result.getName());

        verify(findDinosaurInCachePortMock).findById(dinosaurId);
        verify(getDinosaurByIdPortMock, never()).findById(any());
        verify(saveDinosaurInCachePortMock, never()).save(any(), any());
    }
}
