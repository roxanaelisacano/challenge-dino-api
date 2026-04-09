package ar.com.froneus.challenge.dino.infrastructure.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.com.froneus.challenge.dino.infrastructure.entities.DinosaurEntity;

@Repository
public interface DinosaurRepository extends JpaRepository<DinosaurEntity, Long> {

  boolean existsByName(String name);

  boolean existsByNameAndIdNot(String name, Long id);

  @Query("""
      SELECT d FROM DinosaurEntity d
      WHERE (:species IS NULL OR d.specie.description = :species)
        AND (:discoveryFrom IS NULL OR d.discoveryDate >= :discoveryFrom)
        AND (:discoveryTo   IS NULL OR d.discoveryDate <= :discoveryTo)
        AND (:extinctionFrom IS NULL OR d.extinctionDate >= :extinctionFrom)
        AND (:extinctionTo   IS NULL OR d.extinctionDate <= :extinctionTo)
      """)
  List<DinosaurEntity> findAllWithFilters(
      @Param("species") String species,
      @Param("discoveryFrom") LocalDateTime discoveryFrom,
      @Param("discoveryTo") LocalDateTime discoveryTo,
      @Param("extinctionFrom") LocalDateTime extinctionFrom,
      @Param("extinctionTo") LocalDateTime extinctionTo,
      Pageable pageable);

  @Query("SELECT d FROM DinosaurEntity d WHERE d.status.status = 'ALIVE' AND d.extinctionDate <= :limit")
  List<DinosaurEntity> findAliveToBecomeEndangered(@Param("limit") LocalDateTime limit);

  @Query("SELECT d FROM DinosaurEntity d WHERE d.status.status != 'EXTINCT' AND d.extinctionDate <= :now")
  List<DinosaurEntity> findToBecomeExtinct(@Param("now") LocalDateTime now);
}
