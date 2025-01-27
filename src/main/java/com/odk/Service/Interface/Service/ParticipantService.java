package com.odk.Service.Interface.Service;

import com.odk.Entity.Activite;
import com.odk.Entity.Participant;
import com.odk.Entity.Utilisateur;
import com.odk.Repository.ActiviteRepository;
import com.odk.Repository.ParticipantRepository;
import com.odk.Repository.UtilisateurRepository;
import com.odk.Service.Interface.CrudService;
import com.odk.Utils.UtilService;
import com.odk.dto.ParticipantDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ParticipantService implements CrudService<Participant, Long> {

    private ParticipantRepository participantRepository;
    private UtilisateurRepository utilisateurRepository;
//    private PasswordEncoder passwordEncoder;
//    private RoleRepository roleRepository;
//    private ActiviteParticipantService activiteParticipantService;
    private ActiviteRepository activiteRepository;

    @Override
    public Participant add(Participant entity) {
        return null;
    }

    public Participant addP(Participant participant, Long activiteId) {
        if(!UtilService.isValidEmail(participant.getEmail())) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Votre mail est invalide");
        }

        Optional<Utilisateur> utilisateur = this.utilisateurRepository.findByEmail(participant.getEmail());
        if(utilisateur.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Votre mail est déjà utilisé");
        }

        // Vérification si l'activité existe
        Optional<Activite> activite = activiteRepository.findById(activiteId);
        if(!activite.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "L'activité avec l'ID " + activiteId + " n'existe pas");
        }

        // Association du participant à l'activité
        participant.setActivite(activite.get());

        return participantRepository.save(participant);
    }

    @Override
    public List<Participant> List() {

        return participantRepository.findParticipants();
    }

    public List<ParticipantDTO> listParticipant() {
        return participantRepository.findAll().stream()
                .map(ParticipantDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Participant> findById(Long id) {
        return participantRepository.findById(id);
    }

    @Override
    public Participant update(Participant participant, Long id) {
        Optional<Participant> optionalParticipant = participantRepository.findById(id);
        if (optionalParticipant.isPresent()) {
            Participant participantUpdate = optionalParticipant.get();
            participantUpdate.setNom(participantUpdate.getNom());
            participantUpdate.setEmail(participantUpdate.getEmail());
            participantUpdate.setPrenom(participantUpdate.getPrenom());
            participantUpdate.setPhone(participantUpdate.getPhone());
           return participantRepository.save(participant);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<Participant> optionalParticipant = participantRepository.findById(id);
        optionalParticipant.ifPresent(participant -> participantRepository.delete(participant));
    }

    public Participant checkInParticipant(Long participantId) {
        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Participant non trouvé avec l'ID : " + participantId));

        participant.setCheckedIn(true);
        participant.setCheckInTime(LocalDateTime.now());  // Enregistre l'heure de check-in
        return participantRepository.save(participant);
    }
}
