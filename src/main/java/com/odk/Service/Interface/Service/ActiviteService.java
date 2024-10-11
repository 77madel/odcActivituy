package com.odk.Service.Interface.Service;

import com.odk.Entity.Activite;
import com.odk.Repository.ActiviteRepository;
import com.odk.Service.Interface.CrudService;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ActiviteService implements CrudService<Activite, Long> {

    private ActiviteRepository activiteRepository;
    private PersonnelService personnelService;
    private EmailService emailService;

    @Override
    public Activite add(Activite entity) {
        Activite activiteCree = activiteRepository.save(entity);
        // Récupérer la liste des emails du personnel
        List<String> emailsPersonnel = personnelService.getAllPersonnelEmails();

        // Envoyer un email à chaque personnel
        String sujet = "Nouvelle Activité Créée: " + activiteCree.getNom();
        String message = "Une nouvelle activité a été créée: " + activiteCree.getNom();

        for (String email : emailsPersonnel) {
            emailService.sendSimpleEmail(email, sujet, message);
        }

        return activiteCree;
    }

    @Override
    public List<Activite> List() {
        return activiteRepository.findAll();
    }

    @Override
    public Optional<Activite> findById(Long id) {
        return activiteRepository.findById(id);
    }

    @Override
    public Activite update(Activite activite, Long id) {
        Optional<Activite> activiteOptional = activiteRepository.findById(id);
        if (activiteOptional.isPresent()) {
          return  activiteRepository.save(activite);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<Activite> activiteOptional = activiteRepository.findById(id);
        activiteOptional.ifPresent(activite -> activiteRepository.delete(activite));
    }
}
