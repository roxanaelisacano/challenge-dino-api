package ar.com.froneus.challenge.dino.application.useCases;

import java.time.LocalDateTime;
import java.util.List;

import ar.com.froneus.challenge.dino.application.ports.in.IUpdateDinosaurStatusPort;
import ar.com.froneus.challenge.dino.application.ports.out.IDinoMessagePublisherPort;
import ar.com.froneus.challenge.dino.application.ports.out.IFindDinosuarInBdByStatus;
import ar.com.froneus.challenge.dino.application.ports.out.IEvictDinosaurFromCachePort;
import ar.com.froneus.challenge.dino.application.ports.out.IUpdateDinosaurInDbPort;
import ar.com.froneus.challenge.dino.domain.model.Dinosaur;

public class UpdateDinosaurStatusUseCase implements IUpdateDinosaurStatusPort {
    private final IFindDinosuarInBdByStatus findDinosuarInBdByStatus;
    private final IUpdateDinosaurInDbPort updateDinosaurInDbPort;
    private final IDinoMessagePublisherPort messagePublisher;
    private final IEvictDinosaurFromCachePort cachePort;

    public UpdateDinosaurStatusUseCase(
            IFindDinosuarInBdByStatus findDinosuarInBdByStatus,
            IUpdateDinosaurInDbPort updateDinosaurInDbPort,
            IDinoMessagePublisherPort messagePublisher,
            IEvictDinosaurFromCachePort cachePort) {
        this.findDinosuarInBdByStatus = findDinosuarInBdByStatus;
        this.updateDinosaurInDbPort = updateDinosaurInDbPort;
        this.messagePublisher = messagePublisher;
        this.cachePort = cachePort;
    }

    @Override
    public void execute() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime twentyFourHoursFromNow = now.plusHours(24);

        List<Dinosaur> toEndanger = findDinosuarInBdByStatus.findAliveToBecomeEndangered(twentyFourHoursFromNow);
        toEndanger.forEach(dino -> updateStatusAndNotify(dino, "ENDANGERED"));

        List<Dinosaur> toExtinct = findDinosuarInBdByStatus.findToBecomeExtinct(now);
        toExtinct.forEach(dino -> updateStatusAndNotify(dino, "EXTINCT"));
    }

    private void updateStatusAndNotify(Dinosaur dino, String newStatus) {
        if (!dino.getStatus().equals(newStatus)) {
            dino.setStatus(newStatus);

            updateDinosaurInDbPort.update(dino);

            messagePublisher.publishStatusChange(
                    dino.getId(),
                    newStatus,
                    LocalDateTime.now());

            cachePort.evict(dino.getId());
        }
    }
}