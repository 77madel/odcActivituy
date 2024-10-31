package com.odk.Service.Interface.Service;

import com.odk.Entity.Activite;
import com.odk.Entity.ActiviteParticipant;
import com.odk.Entity.ActiviteParticipantKey;
import com.odk.Entity.Participant;
import com.odk.Repository.ActiviteParticipantRepository;
import com.odk.Repository.ActiviteRepository;
import com.odk.Repository.ParticipantRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class ActiviteParticipantService {


    private ActiviteParticipantRepository activiteParticipantRepository;

    public Map<String, Long> getCountsByGenre() {
        Map<String, Long> counts = new HashMap<>();

        long countHomme = activiteParticipantRepository.countByGenreHomme();
        long countFemme = activiteParticipantRepository.countByGenreFemme();

        counts.put("homme", countHomme);
        counts.put("femme", countFemme);

        return counts;
    }



}
