package ar.com.froneus.challenge.dino.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ar.com.froneus.challenge.dino.domain.ports.ICheckUniqueDinosaurNamePort;
import ar.com.froneus.challenge.dino.domain.validation.DateValidation;
import ar.com.froneus.challenge.dino.domain.validation.DinosaurValidationStrategy;
import ar.com.froneus.challenge.dino.domain.validation.InitialStatusValidation;
import ar.com.froneus.challenge.dino.domain.validation.StatusValidation;
import ar.com.froneus.challenge.dino.domain.validation.UniqueNameValidation;

@Configuration
public class DinosaurValidationConfig {

    @Bean
    public DinosaurValidationStrategy uniqueNameValidation(ICheckUniqueDinosaurNamePort checkUniqueNamePort) {
        return new UniqueNameValidation(checkUniqueNamePort);
    }

    @Bean
    public DinosaurValidationStrategy dateValidation() {
        return new DateValidation();
    }

    @Bean
    public DinosaurValidationStrategy statusValidation() {
        return new StatusValidation();
    }

    @Bean
    public DinosaurValidationStrategy initialStatusValidation() {
        return new InitialStatusValidation();
    }

    @Bean
    public DinosaurValidationStrategy notExtinctUpdateValidation(ar.com.froneus.challenge.dino.domain.ports.IFindDinosaurStatusPort findDinosaurStatusPort) {
        return new ar.com.froneus.challenge.dino.domain.validation.NotExtinctUpdateValidation(findDinosaurStatusPort);
    }
}
