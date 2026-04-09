package ar.com.froneus.challenge.dino.infrastructure.adapters;

import ar.com.froneus.challenge.dino.domain.model.Dinosaur;
import ar.com.froneus.challenge.dino.infrastructure.adapters.out.DinosaurMySqlAdapter;
import ar.com.froneus.challenge.dino.infrastructure.entities.DinosaurEntity;
import ar.com.froneus.challenge.dino.infrastructure.repositories.DinosaurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DinosaurMySqlAdapterTest {

    private DinosaurRepository repositoryMock;
    private DinosaurMySqlAdapter adapter;

    @BeforeEach
    void setUp() {
        repositoryMock = mock(DinosaurRepository.class);
        adapter = new DinosaurMySqlAdapter(repositoryMock);
    }

    @Test
    void shouldMapAndSaveDinosaurCorrectly() {
        LocalDateTime date = LocalDateTime.now();
        Dinosaur model = new Dinosaur(null, "T-Rex", "Rex", date, null, "ALIVE");

        when(repositoryMock.save(any(DinosaurEntity.class))).thenAnswer(invocation -> {
            DinosaurEntity entity = invocation.getArgument(0);
            entity.setId(1L);
            return entity;
        });

        Dinosaur savedModel = adapter.save(model);

        assertNotNull(savedModel.getId());
        assertEquals(1L, savedModel.getId());
        assertEquals("T-Rex", savedModel.getName());
        assertEquals("Rex", savedModel.getSpecies());
        assertEquals("ALIVE", savedModel.getStatus());
        verify(repositoryMock).save(any(DinosaurEntity.class));
    }
}
