package ar.com.froneus.challenge.dino.application.ports.out;

import java.util.List;
import java.util.Optional;

import ar.com.froneus.challenge.dino.domain.model.Dinosaur;
import ar.com.froneus.challenge.dino.application.query.DinosaurFilter;

public interface IFindDinosaursFromCachePort {

    Optional<List<Dinosaur>> findAll(DinosaurFilter filter);
}
