package ar.com.froneus.challenge.dino.application.useCases;

import java.util.List;
import ar.com.froneus.challenge.dino.application.ports.in.ICreateDinosaurPort;
import ar.com.froneus.challenge.dino.application.ports.out.IEvictDinosaurFromCachePort;
import ar.com.froneus.challenge.dino.application.ports.out.ISaveDinosaurPort;
import ar.com.froneus.challenge.dino.domain.model.Dinosaur;
import ar.com.froneus.challenge.dino.domain.validation.DinosaurValidationStrategy;

public class CreateDinosaurUseCase implements ICreateDinosaurPort {

    private final ISaveDinosaurPort saveDinosaurPort;
    private final IEvictDinosaurFromCachePort evictDinosaurFromCachePort;
    private final List<DinosaurValidationStrategy> validations;

    public CreateDinosaurUseCase(
            ISaveDinosaurPort saveDinosaurPort,
            IEvictDinosaurFromCachePort evictDinosaurFromCachePort,
            List<DinosaurValidationStrategy> validations) {
        this.saveDinosaurPort = saveDinosaurPort;
        this.evictDinosaurFromCachePort = evictDinosaurFromCachePort;
        this.validations = validations;
    }

    public Dinosaur execute(Dinosaur dinosaur) {
        validations.forEach(validador -> validador.validate(dinosaur));

        Dinosaur created = saveDinosaurPort.save(dinosaur);

        evictDinosaurFromCachePort.evictLists();

        return created;
    }
}
