package com.odk.Repository;

import com.odk.Entity.Etape;
import com.odk.Enum.Statut;
import jakarta.persistence.TypedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EtapeRepository extends JpaRepository<Etape, Long> {

    List<Etape> findEtapeById(Long id);

    List<Etape> findByStatut(Statut statut);
    @Query("SELECT e FROM Etape e LEFT JOIN FETCH e.critere")
    List<Etape> findAllWithCriteres();
}
