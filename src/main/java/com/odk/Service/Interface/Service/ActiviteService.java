package com.odk.Service.Interface.Service;

import com.odk.Entity.Activite;
import com.odk.Entity.Utilisateur;
import com.odk.Enum.Statut;
import com.odk.Repository.ActiviteRepository;
import com.odk.Service.Interface.CrudService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ActiviteService implements CrudService<Activite, Long> {

    private ActiviteRepository activiteRepository;
    private PersonnelService personnelService;
    private EmailService emailService;
    private UtilisateurService utilisateurService;

    @Override
    public Activite add(Activite entity) {
        try {
            // Enregistrer l'activité
            Activite activiteCree = activiteRepository.save(entity);

            // Récupérer la liste des utilisateurs
            List<Utilisateur> utilisateurs = utilisateurService.List(); // Assurez-vous d'avoir cette méthode

             //  Filtrer les utilisateurs ayant le rôle "personnel"
            List<String> emailsPersonnel = utilisateurs.stream()
                    .filter(utilisateur -> utilisateur.getRole().getNom().equals("Personnel")) // Vérifiez que le rôle est bien défini
                    .map(Utilisateur::getEmail) // Récupérer les emails
                    .collect(Collectors.toList());

            // Préparer le sujet et le message
            String sujet = "Nouvelle Activité Créée: " + activiteCree.getNom();
            String message = "Une nouvelle activité a été créée: " + activiteCree.getNom();

            // Envoyer un email à chaque utilisateur ayant le rôle "personnel"
            for (String email : emailsPersonnel) {
                emailService.sendSimpleEmail(email, sujet, message);
            }

            return activiteCree;
        } catch (DataAccessException e) {
            // Log de l'erreur
            throw new RuntimeException("Erreur d'accès aux données lors de la création de l'activité", e);
        } catch (Exception e) {
            // Log de l'erreur
            throw new RuntimeException("Erreur lors de la création de l'activité", e);
        }
    }
    @Override
    public List<Activite> List() {
        return activiteRepository.findAll();
    }

    public List<Activite> list() {
        // Récupérer toutes les activités
        List<Activite> activites = activiteRepository.findAll();

        // Filtrer les activités dont l'étape a le statut 'EN_COURS'
        List<Activite> activitesEnCours = activites.stream()
                .filter(activite -> false) // Vérifie le statut
                .collect(Collectors.toList());

        return activitesEnCours;
    }

    @Override
    public Optional<Activite> findById(Long id) {
        return activiteRepository.findById(id);
    }

    @Transactional
    @Override
    public Activite update(Activite activite, Long id) {
        return activiteRepository.findById(id).map(a -> {
            if (activite.getNom() != null) {
                a.setNom(activite.getNom());
            }
            if (activite.getTitre() != null) {
                a.setTitre(activite.getTitre());
            }
            if (activite.getDescription() != null) {
                a.setDescription(activite.getDescription());
            }
            if (activite.getDateDebut() != null) {
                a.setDateDebut(activite.getDateDebut());
            }
            if (activite.getLieu() != null) {
                a.setLieu(activite.getLieu());
            }
            if (activite.getObjectifParticipation() != null) {
                a.setObjectifParticipation(activite.getObjectifParticipation());
            }
            if (activite.getEntite() != null) {
                a.setEntite(activite.getEntite());
            }
            if (activite.getTypeActivite() != null) {
                a.setTypeActivite(activite.getTypeActivite());
            }
            if (activite.getEtape() != null) {
                a.getEtape().clear();
                a.getEtape().addAll(activite.getEtape());
            }

            return activiteRepository.save(a);
        }).orElseThrow(() -> new RuntimeException("Votre id n'existe pas"));
    }



    @Override
    public void delete(Long id) {
        Optional<Activite> activiteOptional = activiteRepository.findById(id);
        activiteOptional.ifPresent(activite -> activiteRepository.delete(activite));
    }
}
