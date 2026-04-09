package ar.com.froneus.challenge.dino.application.useCases;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.com.froneus.challenge.dino.application.ports.out.IDeleteDinosaurInDbPort;
import ar.com.froneus.challenge.dino.application.ports.out.IEvictDinosaurFromCachePort;

@ExtendWith(MockitoExtension.class)
public class DeleteDinosaurByIdUseCaseTest {

    private IDeleteDinosaurInDbPort deleteDinosaurInDbPort;
    private IEvictDinosaurFromCachePort evictDinosaurFromCachePort;
    private DeleteDinosaurByIdUseCase useCase;

    private Long dinosaurId;

    @BeforeEach
    void setUp() {
        dinosaurId = 1L;
        deleteDinosaurInDbPort = mock(IDeleteDinosaurInDbPort.class);
        evictDinosaurFromCachePort = mock(IEvictDinosaurFromCachePort.class);
        useCase = new DeleteDinosaurByIdUseCase(evictDinosaurFromCachePort, deleteDinosaurInDbPort);
    }

    @Test
    void executeShouldDeleteDinosaur() {
        doNothing().when(deleteDinosaurInDbPort).delete(dinosaurId);

        assertDoesNotThrow(() -> useCase.execute(dinosaurId));
        verify(deleteDinosaurInDbPort).delete(dinosaurId);
    }
}
