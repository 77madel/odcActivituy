package com.odk.Service.Interface.Service;

import com.odk.Entity.StatistiqueGenre;
import com.odk.Repository.ActiviteParticipantRepository;
import com.odk.Repository.ParticipantRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ReportingService {
    private ActiviteParticipantRepository activiteParticipantRepository;
    private ParticipantRepository participantRepository;

    public List<StatistiqueGenre> StatistiquesParGenre() {
        // Utilisez une requête personnalisée ou QueryDSL
        return activiteParticipantRepository.StatistiquesParGenre();
    }

    public List<StatistiqueGenre> StatistiquesParGenreHomme() {
        // Utilisez une requête personnalisée ou QueryDSL
        return activiteParticipantRepository.StatistiquesParGenre();
    }

    public Map<String, Object> getParticipantStatistics() {
        Map<String, Object> stats = new HashMap<>();

        // Nombre total d'étapes
        stats.put("total", participantRepository.countTotal());

        // Année en cours
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        stats.put("currentYear", currentYear);

        // Nombre d'étapes pour l'année en cours
        stats.put("countForCurrentYear", participantRepository.countByCurrentYear(currentYear));

        return stats;
    }


}
