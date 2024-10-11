package com.odk.Service.Interface.Service;

import com.odk.Entity.Participant;
import com.odk.Entity.Role;
import com.odk.Entity.Utilisateur;
import com.odk.Repository.ParticipantRepository;
import com.odk.Repository.RoleRepository;
import com.odk.Repository.UtilisateurRepository;
import com.odk.Service.Interface.CrudService;
import com.odk.Utils.UtilService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ParticipantService implements CrudService<Participant, Long> {

    private ParticipantRepository participantRepository;
    private UtilisateurRepository utilisateurRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private ActiviteParticipantService activiteParticipantService;

    @Override
    public Participant add(Participant participant) {
        if(!UtilService.isValidEmail(participant.getEmail())) {
            throw new RuntimeException("Votre mail est invalide");
        }

        Optional<Utilisateur> utilisateur = this.utilisateurRepository.findByEmail(participant.getEmail());
        if(utilisateur.isPresent()) {
            throw new RuntimeException("Votre mail est déjà utilisé");
        }

       /* // Définir un mot de passe
        String defaultPassword = "motdepasse123";
        String encodePassword = passwordEncoder.encode(participant.getPassword() != null ? participant.getPassword() : defaultPassword);
        participant.setPassword(encodePassword);

        // Vérifier si le rôle "Participant" existe, sinon le créer et sauvegarder
        Role role = roleRepository.findByNom("Participant").orElseGet(() -> {
            Role newRole = new Role();
            newRole.setNom("Participant");
            return roleRepository.save(newRole);  // Sauvegarder le rôle avant de l'associer
        });

        participant.setRole(role);*/
        return participantRepository.save(participant);
    }

    @Override
    public List<Participant> List() {

        return participantRepository.findParticipants();
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
}
