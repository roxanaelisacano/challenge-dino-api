package ar.com.froneus.challenge.dino.application.query;

import java.time.LocalDateTime;

public class DinosaurFilter {

    private String species;
    private LocalDateTime discoveryDateFrom;
    private LocalDateTime discoveryDateTo;
    private LocalDateTime extinctionDateFrom;
    private LocalDateTime extinctionDateTo;
    private int limit;
    private int offset;

    public DinosaurFilter() {
    }

    public DinosaurFilter(String species,
            LocalDateTime discoveryDateFrom, LocalDateTime discoveryDateTo,
            LocalDateTime extinctionDateFrom, LocalDateTime extinctionDateTo,
            int limit, int offset) {
        this.species = species;
        this.discoveryDateFrom = discoveryDateFrom;
        this.discoveryDateTo = discoveryDateTo;
        this.extinctionDateFrom = extinctionDateFrom;
        this.extinctionDateTo = extinctionDateTo;
        this.limit = limit;
        this.offset = offset;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public LocalDateTime getDiscoveryDateFrom() {
        return discoveryDateFrom;
    }

    public void setDiscoveryDateFrom(LocalDateTime discoveryDateFrom) {
        this.discoveryDateFrom = discoveryDateFrom;
    }

    public LocalDateTime getDiscoveryDateTo() {
        return discoveryDateTo;
    }

    public void setDiscoveryDateTo(LocalDateTime discoveryDateTo) {
        this.discoveryDateTo = discoveryDateTo;
    }

    public LocalDateTime getExtinctionDateFrom() {
        return extinctionDateFrom;
    }

    public void setExtinctionDateFrom(LocalDateTime extinctionDateFrom) {
        this.extinctionDateFrom = extinctionDateFrom;
    }

    public LocalDateTime getExtinctionDateTo() {
        return extinctionDateTo;
    }

    public void setExtinctionDateTo(LocalDateTime extinctionDateTo) {
        this.extinctionDateTo = extinctionDateTo;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
