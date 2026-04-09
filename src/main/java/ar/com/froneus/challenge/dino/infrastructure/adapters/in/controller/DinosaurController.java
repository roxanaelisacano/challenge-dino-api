package ar.com.froneus.challenge.dino.infrastructure.adapters.in.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import ar.com.froneus.challenge.dino.application.ports.in.ICreateDinosaurPort;
import ar.com.froneus.challenge.dino.application.ports.in.IDeleteDinousaurByIdPort;
import ar.com.froneus.challenge.dino.application.ports.in.IGetDinosaurByIdPort;
import ar.com.froneus.challenge.dino.application.ports.in.IGetDinosaursPort;
import ar.com.froneus.challenge.dino.application.ports.in.IUpdateDinosaurPort;
import ar.com.froneus.challenge.dino.domain.model.Dinosaur;
import ar.com.froneus.challenge.dino.application.query.DinosaurFilter;
import ar.com.froneus.challenge.dino.infrastructure.dtos.DinosaurFilterDTO;
import ar.com.froneus.challenge.dino.infrastructure.dtos.DinosaurRequestDTO;
import ar.com.froneus.challenge.dino.infrastructure.dtos.DinosaurResponseDTO;
import ar.com.froneus.challenge.dino.infrastructure.mappers.DinosaurMapper;

@RestController
@RequestMapping("/dinosaurs")
public class DinosaurController {

    private final ICreateDinosaurPort createDinosaurPort;
    private final IGetDinosaursPort getDinosaursPort;
    private final IGetDinosaurByIdPort getDinosaurByIdPort;
    private final IUpdateDinosaurPort updateDinosaurPort;
    private final IDeleteDinousaurByIdPort deleteDinousaurByIdPort;

    public DinosaurController(
            ICreateDinosaurPort createDinosaurPort,
            IGetDinosaursPort getDinosaursPort,
            IGetDinosaurByIdPort getDinosaurByIdPort,
            IUpdateDinosaurPort updateDinosaurPort,
            IDeleteDinousaurByIdPort deleteDinousaurByIdPort) {
        this.createDinosaurPort = createDinosaurPort;
        this.getDinosaursPort = getDinosaursPort;
        this.getDinosaurByIdPort = getDinosaurByIdPort;
        this.updateDinosaurPort = updateDinosaurPort;
        this.deleteDinousaurByIdPort = deleteDinousaurByIdPort;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DinosaurResponseDTO createDinosaur(@RequestBody DinosaurRequestDTO requestDTO) {
        // MAPEAR DTO A DOMINIO
        Dinosaur dinosaur = DinosaurMapper.toDinosaurModel(requestDTO);
        // EJECUTAR USE CASE
        Dinosaur createdDinosaur = createDinosaurPort.execute(dinosaur);
        // MAPEAR DOMINIO A DTO
        return DinosaurMapper.toDinosaurResponseDTO(createdDinosaur);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DinosaurResponseDTO> getDinosaurs(@ModelAttribute DinosaurFilterDTO filterDTO) {
        // MAPEAR DTO A DOMINIO
        DinosaurFilter filter = DinosaurMapper.toDinosaurFilter(filterDTO);
        // EJECUTAR USE CASE (cache -> BD)
        List<Dinosaur> dinosaurs = getDinosaursPort.execute(filter);
        // MAPEAR DOMINIO A DTO
        return dinosaurs.stream()
                .map(DinosaurMapper::toDinosaurResponseDTO)
                .toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DinosaurResponseDTO getDinosaurById(@PathVariable Long id) {
        Dinosaur dinosaur = getDinosaurByIdPort.execute(id);
        return DinosaurMapper.toDinosaurResponseDTO(dinosaur);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DinosaurResponseDTO updateDinosaur(@PathVariable Long id, @RequestBody DinosaurRequestDTO requestDTO) {
        Dinosaur updates = DinosaurMapper.toDinosaurModel(requestDTO);
        Dinosaur updated = updateDinosaurPort.execute(id, updates);
        return DinosaurMapper.toDinosaurResponseDTO(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDinosaur(@PathVariable Long id) {

        deleteDinousaurByIdPort.execute(id);

    }
}
