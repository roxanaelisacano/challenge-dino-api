package ar.com.froneus.challenge.dino.application.ports.in;

import java.util.List;

import ar.com.froneus.challenge.dino.domain.model.Dinosaur;
import ar.com.froneus.challenge.dino.application.query.DinosaurFilter;

public interface IGetDinosaursPort {

    List<Dinosaur> execute(DinosaurFilter filter);
}
