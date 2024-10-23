package com.odk.Service.Interface.Service;

import com.odk.Entity.Activite;
import com.odk.Entity.ActiviteParticipant;
import com.odk.Entity.ActiviteParticipantKey;
import com.odk.Entity.Participant;
import com.odk.Repository.ActiviteParticipantRepository;
import com.odk.Repository.ActiviteRepository;
import com.odk.Repository.ParticipantRepository;

import org.springframework.stereotype.Service;

@Service
public class ActiviteParticipantService {

    private final ActiviteRepository activiteRepository;
    private final ParticipantRepository participantRepository;
    private final ActiviteParticipantRepository activiteParticipantRepository;

    public ActiviteParticipantService(
            ActiviteRepository activiteRepository,
            ParticipantRepository participantRepository,
            ActiviteParticipantRepository activiteParticipantRepository) {
        this.activiteRepository = activiteRepository;
        this.participantRepository = participantRepository;
        this.activiteParticipantRepository = activiteParticipantRepository;
    }

    public void enregistrerParticipantDansActivite(Long activiteId, Long participantId) {
        // Récupération de l'activité et du participant depuis la base de données
        Activite activite = activiteRepository.findById(activiteId)
                .orElseThrow(() -> new RuntimeException("Activité non trouvée"));
        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new RuntimeException("Participant non trouvé"));

        // Création de la clé composite
        ActiviteParticipantKey key = new ActiviteParticipantKey(activiteId, participantId);

        // Création de l'entité ActiviteParticipant
        ActiviteParticipant activiteParticipant = new ActiviteParticipant();
        activiteParticipant.setId(key);
        activiteParticipant.setActivite(activite);
        activiteParticipant.setParticipant(participant);

        // Enregistrement dans la base de données
        activiteParticipantRepository.save(activiteParticipant);
    }


}
