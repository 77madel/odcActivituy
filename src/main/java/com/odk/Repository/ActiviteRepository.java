package com.odk.Repository;

import com.odk.Entity.Activite;
import com.odk.Enum.Statut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ActiviteRepository extends JpaRepository<Activite, Long> {
   Optional<Activite> findByNom(String nom);
   long count();
   @Query("SELECT COUNT(DISTINCT e.activite) FROM Etape e WHERE e.statut = :statut")
   long countActivitesByStatut(@Param("statut") Statut statut);

   long countByStatut(Statut statut);

   // Ou utilisez une requête personnalisée
   @Query("SELECT COUNT(a) FROM Activite a WHERE a.statut = :statut")
   long countByStatutCustom(@Param("statut") Statut statut);

}
