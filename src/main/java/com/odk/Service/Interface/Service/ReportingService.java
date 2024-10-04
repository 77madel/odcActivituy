package com.odk.Service.Interface.Service;

import com.odk.Entity.StatistiqueGenre;
import com.odk.Repository.ActiviteParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportingService {

    @Autowired
    private ActiviteParticipantRepository activiteParticipantRepository;

    public List<StatistiqueGenre> obtenirStatistiquesParGenre() {
        // Utilisez une requête personnalisée ou QueryDSL
        return activiteParticipantRepository.obtenirStatistiquesParGenre();
    }
}
