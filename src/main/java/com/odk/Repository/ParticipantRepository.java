package com.odk.Repository;

import com.odk.Entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    @Query("SELECT u FROM Utilisateur u JOIN u.role r WHERE r.nom = 'Participant'")
    List<Participant> findParticipants();

//    List<Participant> findByEtapeDebut(Long etapeDebutId);
//    List<Participant> findByEtapeResultat(Long etapeResultatId);
}
