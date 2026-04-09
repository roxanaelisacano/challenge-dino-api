package ar.com.froneus.challenge.dino.application.ports.out;

import java.util.List;

import ar.com.froneus.challenge.dino.domain.model.Dinosaur;
import ar.com.froneus.challenge.dino.application.query.DinosaurFilter;

public interface ISaveDinosaursInCachePort {

    void save(DinosaurFilter filter, List<Dinosaur> dinosaurs);
}
