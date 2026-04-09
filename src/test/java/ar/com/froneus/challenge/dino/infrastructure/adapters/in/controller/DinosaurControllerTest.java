package ar.com.froneus.challenge.dino.infrastructure.adapters.in.controller;

import ar.com.froneus.challenge.dino.application.ports.in.ICreateDinosaurPort;
import ar.com.froneus.challenge.dino.application.ports.in.IDeleteDinousaurByIdPort;
import ar.com.froneus.challenge.dino.application.ports.in.IGetDinosaurByIdPort;
import ar.com.froneus.challenge.dino.application.ports.in.IGetDinosaursPort;
import ar.com.froneus.challenge.dino.application.ports.in.IUpdateDinosaurPort;
import ar.com.froneus.challenge.dino.domain.exception.DinosaurNotFoundException;
import ar.com.froneus.challenge.dino.domain.exceptions.DuplicatedDinosaurNameException;
import ar.com.froneus.challenge.dino.domain.model.Dinosaur;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest; // <--- AQUÍ ESTÁ LA SOLUCIÓN
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DinosaurController.class)
public class DinosaurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ICreateDinosaurPort createDinosaurPort;

    @MockitoBean
    private IGetDinosaurByIdPort getDinosaurByIdPort;

    @MockitoBean
    private IGetDinosaursPort getDinosaursPort;

    @MockitoBean
    private IUpdateDinosaurPort updateDinosaurPort;

    @MockitoBean
    private IDeleteDinousaurByIdPort deleteDinosaurPort;

    @Test
    void createDinosaurShouldReturn201AndDinosaurResponseDTOOnSuccess() throws Exception {
        Dinosaur dinosaur = new Dinosaur(1L, "T-Rex", "Rex", LocalDateTime.now(), null, "ALIVE");

        Mockito.when(createDinosaurPort.execute(any(Dinosaur.class))).thenReturn(dinosaur);

        String payload = """
                {
                    "name": "T-Rex",
                    "species": "Rex",
                    "discoveryDate": "2020-01-01T10:10:10",
                    "status": "ALIVE"
                }
                """;

        mockMvc.perform(post("/dinosaurs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("T-Rex"));
    }

    @Test
    void createDinosaurShouldReturn400OnValidationException() throws Exception {
        Mockito.when(createDinosaurPort.execute(any(Dinosaur.class)))
                .thenThrow(new DuplicatedDinosaurNameException("El nombre del dinosaurio 'T-Rex' ya existe."));

        String payload = """
                {
                    "name": "T-Rex",
                    "species": "Rex",
                    "discoveryDate": "2020-01-01T10:10:10",
                    "status": "ALIVE"
                }
                """;

        mockMvc.perform(post("/dinosaurs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("El nombre del dinosaurio 'T-Rex' ya existe."));
    }

    @Test
    void getDinosaurByIdShouldReturn200AndDinosaurResponseDTOOnSuccess() throws Exception {
        Dinosaur dinosaur = new Dinosaur(1L, "T-Rex", "Rex", LocalDateTime.now(), null, "ALIVE");

        Mockito.when(getDinosaurByIdPort.execute(1L)).thenReturn(dinosaur);

        mockMvc.perform(get("/dinosaurs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("T-Rex"));
    }

    @Test
    void getDinosaurByIdShouldReturn404OnDinosaurNotFoundException() throws Exception {
        Mockito.when(getDinosaurByIdPort.execute(1L))
                .thenThrow(new DinosaurNotFoundException(1L));

        mockMvc.perform(get("/dinosaurs/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Dinosaur with id 1 not found"));
    }

    @Test
    void updateDinosaurShouldReturn200AndDinosaurResponseDTOOnSuccess() throws Exception {
        Dinosaur dinosaur = new Dinosaur(1L, "T-Rex", "Rex", LocalDateTime.now(), null, "ALIVE");

        Mockito.when(updateDinosaurPort.execute(any(Long.class), any())).thenReturn(dinosaur);

        String payload = """
                {
                    "name": "T-Rex",
                    "species": "Rex",
                    "discoveryDate": "2020-01-01T10:10:10",
                    "status": "ALIVE"
                }
                """;

        mockMvc.perform(put("/dinosaurs/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("T-Rex"));
    }

    @Test
    void deleteDinosaurShouldReturn204OnSuccess() throws Exception {
        ;

        mockMvc.perform(delete("/dinosaurs/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getDinosaursShouldReturn200AndDinosaurResponseDTOOnSuccess() throws Exception {
        Dinosaur dinosaur = new Dinosaur(1L, "T-Rex", "Rex", LocalDateTime.now(), null, "ALIVE");

        Mockito.when(getDinosaursPort.execute(any())).thenReturn(List.of(dinosaur));

        mockMvc.perform(get("/dinosaurs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].name").value("T-Rex"));
    }

}
