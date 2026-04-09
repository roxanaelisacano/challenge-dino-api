package ar.com.froneus.challenge.dino.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ar.com.froneus.challenge.dino.application.ports.in.ICreateDinosaurPort;
import ar.com.froneus.challenge.dino.application.ports.in.IDeleteDinousaurByIdPort;
import ar.com.froneus.challenge.dino.application.ports.in.IGetDinosaurByIdPort;
import ar.com.froneus.challenge.dino.application.ports.in.IGetDinosaursPort;
import ar.com.froneus.challenge.dino.application.ports.in.IUpdateDinosaurPort;
import ar.com.froneus.challenge.dino.application.ports.in.IUpdateDinosaurStatusPort;
import ar.com.froneus.challenge.dino.application.ports.out.IDeleteDinosaurInDbPort;
import ar.com.froneus.challenge.dino.application.ports.out.IDinoMessagePublisherPort;
import ar.com.froneus.challenge.dino.application.ports.out.IEvictDinosaurFromCachePort;
import ar.com.froneus.challenge.dino.application.ports.out.IFindDinosaurByIdFromCachePort;
import ar.com.froneus.challenge.dino.application.ports.out.IFindDinosaurByIdPort;
import ar.com.froneus.challenge.dino.application.ports.out.IFindDinosaursFromCachePort;
import ar.com.froneus.challenge.dino.application.ports.out.IFindDinosaursPort;
import ar.com.froneus.challenge.dino.application.ports.out.IFindDinosuarInBdByStatus;
import ar.com.froneus.challenge.dino.application.ports.out.ISaveDinosaurByIdInCachePort;
import ar.com.froneus.challenge.dino.application.ports.out.ISaveDinosaurPort;
import ar.com.froneus.challenge.dino.application.ports.out.ISaveDinosaursInCachePort;
import ar.com.froneus.challenge.dino.application.ports.out.IUpdateDinosaurInDbPort;
import ar.com.froneus.challenge.dino.application.useCases.CreateDinosaurUseCase;
import ar.com.froneus.challenge.dino.application.useCases.DeleteDinosaurByIdUseCase;
import ar.com.froneus.challenge.dino.application.useCases.GetDinosaurByIdUseCase;
import ar.com.froneus.challenge.dino.application.useCases.GetDinosaursUseCase;
import ar.com.froneus.challenge.dino.application.useCases.UpdateDinosaurStatusUseCase;
import ar.com.froneus.challenge.dino.application.useCases.UpdateDinosaurUseCase;
import ar.com.froneus.challenge.dino.domain.validation.DinosaurValidationStrategy;
import java.util.List;

@Configuration
public class ApplicationConfig {

    @Bean
    public ICreateDinosaurPort createDinosaurPort(
            ISaveDinosaurPort saveDinosaurPort,
            IEvictDinosaurFromCachePort evictDinosaurFromCachePort,
            List<DinosaurValidationStrategy> validations) {
        return new CreateDinosaurUseCase(saveDinosaurPort, evictDinosaurFromCachePort, validations);
    }

    @Bean
    public IGetDinosaursPort getDinosaursPort(
            IFindDinosaursPort findDinosaursPort,
            IFindDinosaursFromCachePort findDinosaursFromCachePort,
            ISaveDinosaursInCachePort saveDinosaursInCachePort) {
        return new GetDinosaursUseCase(findDinosaursPort, findDinosaursFromCachePort, saveDinosaursInCachePort);
    }

    @Bean
    public IGetDinosaurByIdPort getDinosaurByIdPort(
            IFindDinosaurByIdPort findDinosaurByIdPort,
            IFindDinosaurByIdFromCachePort findDinosaurByIdFromCachePort,
            ISaveDinosaurByIdInCachePort saveDinosaurByIdInCachePort) {
        return new GetDinosaurByIdUseCase(findDinosaurByIdPort, findDinosaurByIdFromCachePort,
                saveDinosaurByIdInCachePort);
    }

    @Bean
    public IUpdateDinosaurPort updateDinosaurPort(
            IFindDinosaurByIdPort findDinosaurByIdPort,
            IUpdateDinosaurInDbPort updateDinosaurInDbPort,
            IEvictDinosaurFromCachePort evictDinosaurFromCachePort,
            List<DinosaurValidationStrategy> validations) {
        return new UpdateDinosaurUseCase(findDinosaurByIdPort, updateDinosaurInDbPort, evictDinosaurFromCachePort,
                validations);
    }

    @Bean
    public IDeleteDinousaurByIdPort deleteDinousaurByIdPort(
            IEvictDinosaurFromCachePort evictDinosaurFromCachePort,
            IDeleteDinosaurInDbPort deleteDinosaurInDbPort) {
        return new DeleteDinosaurByIdUseCase(evictDinosaurFromCachePort, deleteDinosaurInDbPort);
    }

    @Bean
    public IUpdateDinosaurStatusPort updateDinosaurStatusPort(
            IFindDinosuarInBdByStatus findDinosuarInBdByStatus,
            IUpdateDinosaurInDbPort updateDinosaurInDbPort,
            IDinoMessagePublisherPort messagePublisher,
            IEvictDinosaurFromCachePort cachePort) {
        return new UpdateDinosaurStatusUseCase(findDinosuarInBdByStatus, updateDinosaurInDbPort, messagePublisher,
                cachePort);
    }

}
