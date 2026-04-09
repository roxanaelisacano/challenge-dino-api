package ar.com.froneus.challenge.dino.application.useCases;

import java.util.Optional;

import ar.com.froneus.challenge.dino.application.ports.in.IGetDinosaurByIdPort;
import ar.com.froneus.challenge.dino.application.ports.out.IFindDinosaurByIdFromCachePort;
import ar.com.froneus.challenge.dino.application.ports.out.IFindDinosaurByIdPort;
import ar.com.froneus.challenge.dino.application.ports.out.ISaveDinosaurByIdInCachePort;
import ar.com.froneus.challenge.dino.domain.exception.DinosaurNotFoundException;
import ar.com.froneus.challenge.dino.domain.model.Dinosaur;

public class GetDinosaurByIdUseCase implements IGetDinosaurByIdPort {

    private final IFindDinosaurByIdPort findDinosaurByIdPort;
    private final IFindDinosaurByIdFromCachePort findDinosaurByIdFromCachePort;
    private final ISaveDinosaurByIdInCachePort saveDinosaurByIdInCachePort;

    public GetDinosaurByIdUseCase(
            IFindDinosaurByIdPort findDinosaurByIdPort,
            IFindDinosaurByIdFromCachePort findDinosaurByIdFromCachePort,
            ISaveDinosaurByIdInCachePort saveDinosaurByIdInCachePort) {
        this.findDinosaurByIdPort = findDinosaurByIdPort;
        this.findDinosaurByIdFromCachePort = findDinosaurByIdFromCachePort;
        this.saveDinosaurByIdInCachePort = saveDinosaurByIdInCachePort;
    }

    @Override
    public Dinosaur execute(Long id) {
        Optional<Dinosaur> cached = findDinosaurByIdFromCachePort.findById(id);
        if (cached.isPresent()) {
            return cached.get();
        }

        Dinosaur dinosaur = findDinosaurByIdPort.findById(id)
                .orElseThrow(() -> new DinosaurNotFoundException(id));

        saveDinosaurByIdInCachePort.save(id, dinosaur);

        return dinosaur;
    }
}
