package ar.com.froneus.challenge.dino.infrastructure.adapters.out;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import ar.com.froneus.challenge.dino.application.ports.out.IDeleteDinosaurInDbPort;
import ar.com.froneus.challenge.dino.application.ports.out.IFindDinosaurByIdPort;
import ar.com.froneus.challenge.dino.application.ports.out.IFindDinosaursPort;
import ar.com.froneus.challenge.dino.application.ports.out.IFindDinosuarInBdByStatus;
import ar.com.froneus.challenge.dino.application.ports.out.ISaveDinosaurPort;
import ar.com.froneus.challenge.dino.application.ports.out.IUpdateDinosaurInDbPort;
import ar.com.froneus.challenge.dino.domain.exception.DinosaurNotFoundException;
import ar.com.froneus.challenge.dino.domain.model.Dinosaur;
import ar.com.froneus.challenge.dino.application.query.DinosaurFilter;
import ar.com.froneus.challenge.dino.infrastructure.entities.DinosaurEntity;
import ar.com.froneus.challenge.dino.infrastructure.mappers.DinosaurMapper;
import ar.com.froneus.challenge.dino.infrastructure.repositories.DinosaurRepository;

@Repository
public class DinosaurMySqlAdapter implements ISaveDinosaurPort, IFindDinosaursPort, IFindDinosaurByIdPort,
        IUpdateDinosaurInDbPort, IDeleteDinosaurInDbPort, IFindDinosuarInBdByStatus {

    private final DinosaurRepository dinosaurRepository;

    public DinosaurMySqlAdapter(DinosaurRepository dinosaurRepository) {
        this.dinosaurRepository = dinosaurRepository;
    }

    @Override
    public Dinosaur save(Dinosaur dinosaur) {
        DinosaurEntity entity = DinosaurMapper.toDinosaurEntity(dinosaur);
        DinosaurEntity savedEntity = dinosaurRepository.save(entity);
        return DinosaurMapper.toDinosaurModel(savedEntity);
    }

    @Override
    public List<Dinosaur> findAll(DinosaurFilter filter) {
        PageRequest pageRequest = PageRequest.of(
                filter.getOffset() / Math.max(filter.getLimit(), 1),
                Math.max(filter.getLimit(), 1));

        List<DinosaurEntity> entities = dinosaurRepository.findAllWithFilters(
                filter.getSpecies(),
                filter.getDiscoveryDateFrom(),
                filter.getDiscoveryDateTo(),
                filter.getExtinctionDateFrom(),
                filter.getExtinctionDateTo(),
                pageRequest);

        return entities.stream()
                .map(DinosaurMapper::toDinosaurModel)
                .toList();
    }

    @Override
    public Optional<Dinosaur> findById(Long id) {
        return dinosaurRepository.findById(id)
                .map(DinosaurMapper::toDinosaurModel);
    }

    @Override
    public Dinosaur update(Dinosaur dinosaur) {
        DinosaurEntity entity = dinosaurRepository.findById(dinosaur.getId())
                .orElseThrow(() -> new DinosaurNotFoundException(dinosaur.getId()));

        entity.setName(dinosaur.getName());
        entity.getSpecie().setDescription(dinosaur.getSpecies());
        entity.getStatus().setStatus(dinosaur.getStatus());
        entity.setDiscoveryDate(dinosaur.getDiscoveryDate());
        entity.setExtinctionDate(dinosaur.getExtinctionDate());

        return DinosaurMapper.toDinosaurModel(dinosaurRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        dinosaurRepository.deleteById(id);
    }

    @Override
    public List<Dinosaur> findAliveToBecomeEndangered(LocalDateTime limit) {
        return dinosaurRepository.findAliveToBecomeEndangered(limit).stream()
                .map(DinosaurMapper::toDinosaurModel)
                .toList();
    }

    @Override
    public List<Dinosaur> findToBecomeExtinct(LocalDateTime now) {
        return dinosaurRepository.findToBecomeExtinct(now).stream()
                .map(DinosaurMapper::toDinosaurModel)
                .toList();
    }
}
