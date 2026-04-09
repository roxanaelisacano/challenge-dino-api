package ar.com.froneus.challenge.dino.infrastructure.adapters.in.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ar.com.froneus.challenge.dino.application.ports.in.IUpdateDinosaurStatusPort;

@Component
public class DinosaurStatusScheduler {

    private final IUpdateDinosaurStatusPort updateStatusUseCase;

    public DinosaurStatusScheduler(IUpdateDinosaurStatusPort updateStatusUseCase) {
        this.updateStatusUseCase = updateStatusUseCase;
    }

    @Scheduled(cron = "0 */10 * * * *")
    public void checkAndUpdateDinosaurStatuses() {
        updateStatusUseCase.execute();
    }
}
