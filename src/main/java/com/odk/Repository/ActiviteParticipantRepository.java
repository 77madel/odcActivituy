package com.odk.Repository;

import com.odk.Entity.ActiviteParticipant;
import com.odk.Entity.ActiviteParticipantKey;
import com.odk.Entity.StatistiqueGenre;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface ActiviteParticipantRepository extends JpaRepository<ActiviteParticipant, ActiviteParticipantKey> {

   /* @Query("SELECT new com.odk.Entity.StatistiqueGenre(u.genre, COUNT(ap.participant.id)) " +
            "FROM ActiviteParticipant ap JOIN ap.participant p JOIN Utilisateur u ON p.id = u.id " +
            "GROUP BY u.genre")
    List<StatistiqueGenre> StatistiquesParGenre();*/

    @Query("SELECT new com.odk.Entity.StatistiqueGenre(p.genre, COUNT(p)) " +
            "FROM ActiviteParticipant ap JOIN ap.participant p " +
            "GROUP BY p.genre")
    List<StatistiqueGenre> StatistiquesParGenre();


    @Query("SELECT COUNT(ap) FROM ActiviteParticipant ap JOIN ap.participant p WHERE LOWER(TRIM(p.genre)) = 'homme'")
    long countByGenreHomme();

    @Query("SELECT COUNT(ap) FROM ActiviteParticipant ap JOIN ap.participant p WHERE LOWER(TRIM(p.genre)) = 'femme'")
    long countByGenreFemme();

    @Modifying
    @Transactional
    @Query("DELETE FROM ActiviteParticipant ap WHERE ap.participant.id = :participantId")
    void deleteByParticipantId(@Param("participantId") Long participantId);





}

