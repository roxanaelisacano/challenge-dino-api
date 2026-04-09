package ar.com.froneus.challenge.dino.application.useCases;

import java.util.List;
import java.util.Optional;

import ar.com.froneus.challenge.dino.application.ports.in.IGetDinosaursPort;
import ar.com.froneus.challenge.dino.application.ports.out.IFindDinosaursFromCachePort;
import ar.com.froneus.challenge.dino.application.ports.out.IFindDinosaursPort;
import ar.com.froneus.challenge.dino.application.ports.out.ISaveDinosaursInCachePort;
import ar.com.froneus.challenge.dino.domain.model.Dinosaur;
import ar.com.froneus.challenge.dino.application.query.DinosaurFilter;

public class GetDinosaursUseCase implements IGetDinosaursPort {

    private final IFindDinosaursPort findDinosaursPort;
    private final IFindDinosaursFromCachePort findDinosaursFromCachePort;
    private final ISaveDinosaursInCachePort saveDinosaursInCachePort;

    public GetDinosaursUseCase(
            IFindDinosaursPort findDinosaursPort,
            IFindDinosaursFromCachePort findDinosaursFromCachePort,
            ISaveDinosaursInCachePort saveDinosaursInCachePort) {
        this.findDinosaursPort = findDinosaursPort;
        this.findDinosaursFromCachePort = findDinosaursFromCachePort;
        this.saveDinosaursInCachePort = saveDinosaursInCachePort;
    }

    @Override
    public List<Dinosaur> execute(DinosaurFilter filter) {
        Optional<List<Dinosaur>> cached = findDinosaursFromCachePort.findAll(filter);
        if (cached.isPresent()) {
            return cached.get();
        }

        List<Dinosaur> dinosaurs = findDinosaursPort.findAll(filter);

        saveDinosaursInCachePort.save(filter, dinosaurs);

        return dinosaurs;
    }
}
