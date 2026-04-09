package ar.com.froneus.challenge.dino.application.useCases;

import ar.com.froneus.challenge.dino.application.ports.in.IDeleteDinousaurByIdPort;
import ar.com.froneus.challenge.dino.application.ports.out.IDeleteDinosaurInDbPort;
import ar.com.froneus.challenge.dino.application.ports.out.IEvictDinosaurFromCachePort;

public class DeleteDinosaurByIdUseCase implements IDeleteDinousaurByIdPort {
    private final IEvictDinosaurFromCachePort evictDinosaurFromCachePort;
    private final IDeleteDinosaurInDbPort deleteDinosaurInDbPort;

    public DeleteDinosaurByIdUseCase(
            IEvictDinosaurFromCachePort evictDinosaurFromCachePort, IDeleteDinosaurInDbPort deleteDinosaurInDbPort) {
        this.evictDinosaurFromCachePort = evictDinosaurFromCachePort;
        this.deleteDinosaurInDbPort = deleteDinosaurInDbPort;
    }

    @Override
    public void execute(Long id) {
        deleteDinosaurInDbPort.delete(id);
        evictDinosaurFromCachePort.evict(id);
    }
}
