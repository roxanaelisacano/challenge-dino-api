package ar.com.froneus.challenge.dino.domain.model;

import java.time.LocalDateTime;

public class Dinosaur {
    private Long id;
    private String name;
    private String species;
    private LocalDateTime discoveryDate;
    private LocalDateTime extinctionDate;
    private String status;

    public Dinosaur() {
    }

    public Dinosaur(Long id, String name, String species, LocalDateTime discoveryDate, LocalDateTime extinctionDate,
            String status) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.discoveryDate = discoveryDate;
        this.extinctionDate = extinctionDate;
        this.status = status;
    }

    public Dinosaur(String name, String species, LocalDateTime discoveryDate, LocalDateTime extinctionDate,
            String status) {
        this.name = name;
        this.species = species;
        this.discoveryDate = discoveryDate;
        this.extinctionDate = extinctionDate;
        this.status = status;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public LocalDateTime getDiscoveryDate() {
        return discoveryDate;
    }

    public void setDiscoveryDate(LocalDateTime discoveryDate) {
        this.discoveryDate = discoveryDate;
    }

    public LocalDateTime getExtinctionDate() {
        return extinctionDate;
    }

    public void setExtinctionDate(LocalDateTime extinctionDate) {
        this.extinctionDate = extinctionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Dinosaur [name=" + name + ", species=" + species + ", discoveryDate=" + discoveryDate
                + ", extinctionDate=" + extinctionDate + ", status=" + status + "]";
    }

    public void applyUpdates(Dinosaur updates) {
        this.name = updates.name;
        this.species = updates.species;
        this.discoveryDate = updates.discoveryDate;
        this.extinctionDate = updates.extinctionDate;
        this.status = updates.status;
    }

}
