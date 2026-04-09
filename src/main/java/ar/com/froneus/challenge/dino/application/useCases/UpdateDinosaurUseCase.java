package ar.com.froneus.challenge.dino.application.useCases;

import java.util.List;

import ar.com.froneus.challenge.dino.application.ports.in.IUpdateDinosaurPort;
import ar.com.froneus.challenge.dino.application.ports.out.IEvictDinosaurFromCachePort;
import ar.com.froneus.challenge.dino.application.ports.out.IFindDinosaurByIdPort;
import ar.com.froneus.challenge.dino.application.ports.out.IUpdateDinosaurInDbPort;
import ar.com.froneus.challenge.dino.domain.exception.DinosaurNotFoundException;
import ar.com.froneus.challenge.dino.domain.model.Dinosaur;
import ar.com.froneus.challenge.dino.domain.validation.DinosaurValidationStrategy;

public class UpdateDinosaurUseCase implements IUpdateDinosaurPort {

    private final IFindDinosaurByIdPort findDinosaurByIdPort;
    private final IUpdateDinosaurInDbPort updateDinosaurInDbPort;
    private final IEvictDinosaurFromCachePort evictDinosaurFromCachePort;
    private final List<DinosaurValidationStrategy> validations;

    public UpdateDinosaurUseCase(
            IFindDinosaurByIdPort findDinosaurByIdPort,
            IUpdateDinosaurInDbPort updateDinosaurInDbPort,
            IEvictDinosaurFromCachePort evictDinosaurFromCachePort,
            List<DinosaurValidationStrategy> validations) {
        this.findDinosaurByIdPort = findDinosaurByIdPort;
        this.updateDinosaurInDbPort = updateDinosaurInDbPort;
        this.evictDinosaurFromCachePort = evictDinosaurFromCachePort;
        this.validations = validations;
    }

    @Override
    public Dinosaur execute(Long id, Dinosaur updates) {
        Dinosaur existing = findDinosaurByIdPort.findById(id)
                .orElseThrow(() -> new DinosaurNotFoundException(id));

        existing.applyUpdates(updates);

        validations.forEach(v -> v.validate(existing));

        Dinosaur updated = updateDinosaurInDbPort.update(existing);

        evictDinosaurFromCachePort.evict(id);

        return updated;
    }
}
