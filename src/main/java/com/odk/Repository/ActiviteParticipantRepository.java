package com.odk.Repository;

import com.odk.Entity.ActiviteParticipant;
import com.odk.Entity.ActiviteParticipantKey;
import com.odk.Entity.StatistiqueGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActiviteParticipantRepository extends JpaRepository<ActiviteParticipant, ActiviteParticipantKey> {

    @Query("SELECT new com.odk.Entity.StatistiqueGenre(" +
            "FUNCTION('DATE_FORMAT', ap.dateFormation, '%Y-%m'), u.genre, COUNT(DISTINCT ap.participant.id)) " +
            "FROM ActiviteParticipant ap " +
            "JOIN ap.participant p " +
            "JOIN Utilisateur u ON p.id = u.id " +
            "GROUP BY FUNCTION('DATE_FORMAT', ap.dateFormation, '%Y-%m'), u.genre " +
            "ORDER BY FUNCTION('DATE_FORMAT', ap.dateFormation, '%Y-%m'), u.genre")
    List<StatistiqueGenre> obtenirStatistiquesParGenre();
}
