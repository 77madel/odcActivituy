package com.odk.Repository;

import com.odk.Entity.ActiviteParticipant;
import com.odk.Entity.ActiviteParticipantKey;
import com.odk.Entity.StatistiqueGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActiviteParticipantRepository extends JpaRepository<ActiviteParticipant, ActiviteParticipantKey> {

    @Query("SELECT new com.odk.Entity.StatistiqueGenre(u.genre, COUNT(ap.participant.id)) " +
            "FROM ActiviteParticipant ap JOIN ap.participant p JOIN Utilisateur u ON p.id = u.id " +
            "GROUP BY u.genre")
    List<StatistiqueGenre> StatistiquesParGenre();

    @Query("SELECT new com.odk.Entity.StatistiqueGenre(p.genre, COUNT(ap.participant.id)) " +
            "FROM ActiviteParticipant ap JOIN ap.participant p " +
            "WHERE p.genre = 'Homme' " +
            "GROUP BY p.genre")
    List<StatistiqueGenre> StatistiquesParGenreHomme();



}

