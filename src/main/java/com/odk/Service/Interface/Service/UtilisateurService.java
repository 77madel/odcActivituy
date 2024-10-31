package com.odk.Service.Interface.Service;

import com.odk.Entity.Role;
import com.odk.Entity.Utilisateur;
import com.odk.Repository.RoleRepository;
import com.odk.Repository.UtilisateurRepository;
import com.odk.Service.Interface.CrudService;
import com.odk.Utils.UtilService;
import com.odk.dto.UtilisateurDTO;
import com.odk.execption.IncorrectPasswordException;
import com.odk.execption.UtilisateurNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UtilisateurService implements UserDetailsService, CrudService<Utilisateur, Long> {

    private UtilisateurRepository utilisateurRepository;
    private PasswordEncoder passwordEncoder;
    private EmailService emailService;
    private RoleRepository roleRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return utilisateurRepository.findByEmail(username).orElseThrow();
    }

    @Override
    public Utilisateur add(Utilisateur utilisateur) {
        if (!UtilService.isValidEmail(utilisateur.getEmail())) {
            throw new RuntimeException("Votre mail est invalide");
        }

        Optional<Utilisateur> utilisateur1 = this.utilisateurRepository.findByEmail(utilisateur.getEmail());
        if (utilisateur1.isPresent()) {
            throw new RuntimeException("Votre mail est déjà utilisé");
        }

        // Définir un mot de passe par défaut si aucun mot de passe n'est fourni
        String defaultPassword = "motdepasse123";
        String rawPassword = utilisateur.getPassword() != null ? utilisateur.getPassword() : defaultPassword;

        // Encoder le mot de passe pour le stockage
        String encodedPassword = passwordEncoder.encode(rawPassword);
        utilisateur.setPassword(encodedPassword);

        // Vérifiez si le rôle est null avant d'accéder à ses propriétés
        if (utilisateur.getRole() == null || utilisateur.getRole().getNom() == null) {
            throw new RuntimeException("Le rôle ne peut pas être null");
        }

        // Rechercher le rôle par son nom
        Role role = roleRepository.findByNom(utilisateur.getRole().getNom())
                .orElseThrow(() -> new RuntimeException("Le rôle " + utilisateur.getRole().getNom() + " n'existe pas"));

        utilisateur.setRole(role);
        Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);

        // Envoyer un email avec le mot de passe et les informations du personnel
        String sujet = "Bienvenue dans notre équipe";
        String message = "Bonjour " + utilisateur.getNom() + ",\n\n"
                + "Votre compte a été créé avec succès. Voici vos informations de connexion :\n"
                + "Email : " + utilisateur.getEmail() + "\n"
                + "Mot de passe : " + rawPassword + "\n\n"
                + "Veuillez changer votre mot de passe après votre première connexion.\n\n"
                + "Cordialement,\n ODC.";
        emailService.sendSimpleEmail(utilisateur.getEmail(), sujet, message);

        return savedUtilisateur;
    }

    @Override
    public List<Utilisateur> List() {
        return utilisateurRepository.findAll();
    }

    public List<UtilisateurDTO> getAllUtilisateur() {
        return utilisateurRepository.findAll().stream()
                .map(this::convertToDTO) // Conversion de l'entité à DTO
                .collect(Collectors.toList());
    }

    private UtilisateurDTO convertToDTO(Utilisateur utilisateur) {
        return new UtilisateurDTO(
                utilisateur.getId(),
                utilisateur.getNom(),
                utilisateur.getPrenom(),
                utilisateur.getEmail(),
                utilisateur.getPhone(),
                utilisateur.getGenre(),
                utilisateur.getRole(),
                utilisateur.getEntite()
        );
    }

    @Override
    public Optional<Utilisateur> findById(Long id) {
        return utilisateurRepository.findById(id);
    }

    @Override
    public Utilisateur update(Utilisateur utilisateur, Long id) {
        return utilisateurRepository.findById(id).map(
                p -> {
                    p.setNom(utilisateur.getNom());
                    p.setEmail(utilisateur.getEmail());
                    p.setPrenom(utilisateur.getPrenom());
                    p.setPhone(utilisateur.getPhone());

                    // Vérifiez si le rôle est null avant de le définir
                    if (utilisateur.getRole() != null) {
                        p.setRole(utilisateur.getRole());
                    }

                    if (utilisateur.getEntite() != null) {
                        p.setEntite(utilisateur.getEntite());
                    }

                    // Si le mot de passe est modifié, encodez-le
                    if (utilisateur.getPassword() != null) {
                        p.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
                    }

                    return utilisateurRepository.save(p);
                }).orElseThrow(() -> new RuntimeException("Votre id n'existe pas"));
    }

    @Override
    public void delete(Long id) {
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(id);
        optionalUtilisateur.ifPresent(personnel -> utilisateurRepository.deleteById(id));

    }

    public long getNombreUtilisateurs() {
        return utilisateurRepository.count(); // Retourne le nombre d'utilisateurs
    }

    public void modifierMotDePasse(Map<String, String> parametres) {
        String ancienMotDePasse = parametres.get("ancienPassword");
        String nouveauMotDePasse = parametres.get("newPassword");

        // Obtenir l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // On suppose que le nom de l'utilisateur est son email

        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findByEmail(email);

        if (optionalUtilisateur.isPresent()) {
            Utilisateur utilisateur = optionalUtilisateur.get();

            // Vérifier si l'ancien mot de passe est correct
            if (passwordEncoder.matches(ancienMotDePasse, utilisateur.getPassword())) {
                // Vérifiez si le nouveau mot de passe est différent de l'ancien
                if (!ancienMotDePasse.equals(nouveauMotDePasse)) {
                    utilisateur.setPassword(passwordEncoder.encode(nouveauMotDePasse));
                    utilisateurRepository.save(utilisateur);
                } else {
                    throw new IllegalArgumentException("Le nouveau mot de passe ne peut pas être le même que l'ancien.");
                }
            } else {
                throw new IllegalArgumentException("L'ancien mot de passe est incorrect.");
            }
        } else {
            throw new NoSuchElementException("Utilisateur avec cet email n'existe pas.");
        }
    }



}
