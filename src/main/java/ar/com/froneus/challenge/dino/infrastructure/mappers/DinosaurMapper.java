package ar.com.froneus.challenge.dino.infrastructure.mappers;

import ar.com.froneus.challenge.dino.domain.model.Dinosaur;
import ar.com.froneus.challenge.dino.application.query.DinosaurFilter;
import ar.com.froneus.challenge.dino.infrastructure.dtos.DinosaurFilterDTO;
import ar.com.froneus.challenge.dino.infrastructure.dtos.DinosaurRequestDTO;
import ar.com.froneus.challenge.dino.infrastructure.dtos.DinosaurResponseDTO;
import ar.com.froneus.challenge.dino.infrastructure.entities.DinoSpecieEntity;
import ar.com.froneus.challenge.dino.infrastructure.entities.DinoStatusEntity;
import ar.com.froneus.challenge.dino.infrastructure.entities.DinosaurEntity;

public class DinosaurMapper {

    public static Dinosaur toDinosaurModel(DinosaurRequestDTO requestDTO) {
        return new Dinosaur(requestDTO.name(), requestDTO.species(), requestDTO.discoveryDate(),
                requestDTO.extinctionDate(), requestDTO.status());
    }

    public static DinosaurResponseDTO toDinosaurResponseDTO(Dinosaur dinosaur) {
        return new DinosaurResponseDTO(dinosaur.getId(), dinosaur.getName(), dinosaur.getSpecies(),
                dinosaur.getDiscoveryDate(), dinosaur.getExtinctionDate(), dinosaur.getStatus());
    }

    public static DinosaurEntity toDinosaurEntity(Dinosaur dinosaur) {
        DinoSpecieEntity specieEntity = new DinoSpecieEntity();
        specieEntity.setDescription(dinosaur.getSpecies());

        DinoStatusEntity statusEntity = new DinoStatusEntity();
        statusEntity.setStatus(dinosaur.getStatus());

        return new DinosaurEntity(
                dinosaur.getName(),
                specieEntity,
                dinosaur.getDiscoveryDate(),
                dinosaur.getExtinctionDate(),
                statusEntity);
    }

    public static Dinosaur toDinosaurModel(DinosaurEntity entity) {
        return new Dinosaur(
                entity.getId(),
                entity.getName(),
                entity.getSpecie().getDescription(),
                entity.getDiscoveryDate(),
                entity.getExtinctionDate(),
                entity.getStatus().getStatus());
    }

    public static DinosaurFilter toDinosaurFilter(DinosaurFilterDTO dto) {
        return new DinosaurFilter(
                dto.species(),
                dto.discoveryDateFrom(),
                dto.discoveryDateTo(),
                dto.extinctionDateFrom(),
                dto.extinctionDateTo(),
                dto.limit(),
                dto.offset());
    }
}

