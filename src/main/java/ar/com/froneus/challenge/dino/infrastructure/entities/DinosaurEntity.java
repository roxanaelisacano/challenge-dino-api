package ar.com.froneus.challenge.dino.infrastructure.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;

@Entity
@Table(name = "dinosaurs")
public class DinosaurEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "specie_id")
    private DinoSpecieEntity specie;
    private LocalDateTime discoveryDate;
    private LocalDateTime extinctionDate;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id")
    private DinoStatusEntity status;

    protected DinosaurEntity() {}

    public DinosaurEntity(String name, DinoSpecieEntity specie, LocalDateTime discoveryDate,
            LocalDateTime extinctionDate,
            DinoStatusEntity status) {
        this.name = name;
        this.specie = specie;
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

    public DinoSpecieEntity getSpecie() {
        return specie;
    }

    public void setSpecies(DinoSpecieEntity specie) {
        this.specie = specie;
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

    public DinoStatusEntity getStatus() {
        return status;
    }

    public void setStatus(DinoStatusEntity status) {
        this.status = status;
    }

}
