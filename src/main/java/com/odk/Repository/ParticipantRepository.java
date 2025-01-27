package com.odk.Repository;

import com.odk.Entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    @Query("SELECT u FROM Utilisateur u JOIN u.role r WHERE r.nom = 'Participant'")
    List<Participant> findParticipants();

    // Compte le nombre total d'enregistrements
    @Query("SELECT COUNT(p) FROM Participant p")
    long countTotal();

    // Compte les enregistrements de l'année en cours
    @Query("SELECT COUNT(p) FROM Participant p WHERE YEAR(p.createdAt) = :currentYear")
    long countByCurrentYear(@Param("currentYear") int currentYear);

//    List<Participant> findByEtapeDebut(Long etapeDebutId);
//    List<Participant> findByEtapeResultat(Long etapeResultatId);
}
