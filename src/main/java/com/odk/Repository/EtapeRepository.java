package com.odk.Repository;

import com.odk.Entity.Etape;
import com.odk.Enum.Statut;
import jakarta.persistence.TypedQuery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EtapeRepository extends JpaRepository<Etape, Long> {

    List<Etape> findEtapeById(Long id);

    List<Etape> findByStatut(Statut statut);
}
