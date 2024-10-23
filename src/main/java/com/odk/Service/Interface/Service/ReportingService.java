package com.odk.Service.Interface.Service;

import com.odk.Entity.StatistiqueGenre;
import com.odk.Repository.ActiviteParticipantRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReportingService {
    private ActiviteParticipantRepository activiteParticipantRepository;

    public List<StatistiqueGenre> StatistiquesParGenre() {
        // Utilisez une requête personnalisée ou QueryDSL
        return activiteParticipantRepository.StatistiquesParGenre();
    }

    public List<StatistiqueGenre> StatistiquesParGenreHomme() {
        // Utilisez une requête personnalisée ou QueryDSL
        return activiteParticipantRepository.StatistiquesParGenreHomme();
    }


}
