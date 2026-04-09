package ar.com.froneus.challenge.dino.infrastructure.adapters.out;

import org.springframework.stereotype.Component;

import ar.com.froneus.challenge.dino.domain.ports.ICheckUniqueDinosaurNamePort;
import ar.com.froneus.challenge.dino.domain.ports.IFindDinosaurStatusPort;
import ar.com.froneus.challenge.dino.infrastructure.repositories.DinosaurRepository;

@Component
public class DinosaurDbValidationAdapter implements ICheckUniqueDinosaurNamePort, IFindDinosaurStatusPort {

    private final DinosaurRepository dinosaurRepository;

    public DinosaurDbValidationAdapter(DinosaurRepository dinosaurRepository) {
        this.dinosaurRepository = dinosaurRepository;
    }

    @Override
    public boolean isNameTaken(String name, Long excludeId) {
        if (excludeId == null) {
            return dinosaurRepository.existsByName(name);
        } else {
            return dinosaurRepository.existsByNameAndIdNot(name, excludeId);
        }
    }

    @Override
    public String findOriginalStatus(Long id) {
        return dinosaurRepository.findById(id)
                .map(entity -> entity.getStatus().getStatus())
                .orElse(null);
    }
}
