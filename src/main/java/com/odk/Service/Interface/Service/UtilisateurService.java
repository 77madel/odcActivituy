package com.odk.Service.Interface.Service;

import com.odk.Entity.Entite;
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
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Votre mail est invalide");
        }

        Optional<Utilisateur> utilisateur1 = this.utilisateurRepository.findByEmail(utilisateur.getEmail());
        if (utilisateur1.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Votre mail est déjà utilisé");
        }

        // Définir un mot de passe par défaut si aucun mot de passe n'est fourni
        String defaultPassword = "motdepasse123";
        String rawPassword = utilisateur.getPassword() != null ? utilisateur.getPassword() : defaultPassword;

        // Encoder le mot de passe pour le stockage
        String encodedPassword = passwordEncoder.encode(rawPassword);
        utilisateur.setPassword(encodedPassword);

        // Vérifiez si le rôle est null avant d'accéder à ses propriétés
        if (utilisateur.getRole() == null || utilisateur.getRole().getId() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Le rôle ne peut pas être null");
        }

        // Rechercher le rôle par son nom
        Role role = roleRepository.findById(utilisateur.getRole().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Le rôle " + utilisateur.getRole().getId() + " n'existe pas"));

        utilisateur.setRole(role);
        Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);


        // Construire le corps de l'email avec HTML pour une meilleure présentation
        StringBuilder emailBodyBuilder = new StringBuilder();
        emailBodyBuilder.append("<!DOCTYPE html>");
        emailBodyBuilder.append("<html lang=\"fr\">");
        emailBodyBuilder.append("<head>");
        emailBodyBuilder.append("<meta charset=\"UTF-8\">");
        emailBodyBuilder.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        emailBodyBuilder.append("<title>Confirmation de Création de Compte</title>");
        emailBodyBuilder.append("<style>");
        emailBodyBuilder.append("  body { font-family: Arial, sans-serif; background-color: #f39c12; margin: 0; padding: 20px; }");
        emailBodyBuilder.append("  .container { background-color: #ffffff; padding: 20px; border-radius: 5px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }");
        emailBodyBuilder.append("  .header { text-align: center; padding-bottom: 20px; }");
        emailBodyBuilder.append("  .content { line-height: 1.6; }");
        emailBodyBuilder.append("  .footer { margin-top: 20px; font-size: 0.9em; color: #555555; text-align: center; }");
        emailBodyBuilder.append("</style>");
        emailBodyBuilder.append("</head>");
        emailBodyBuilder.append("<body>");
        emailBodyBuilder.append("<div class=\"container\">");
        emailBodyBuilder.append("<div class=\"header\">");
        emailBodyBuilder.append("<h2>Bienvenue chez Orange Digital Center</h2>");
        emailBodyBuilder.append("</div>");
        emailBodyBuilder.append("<div class=\"content\">");
        emailBodyBuilder.append("<p>Bonjour ").append(utilisateur.getPrenom()).append(" ").append(utilisateur.getNom()).append(",</p>");
        emailBodyBuilder.append("<p>Nous sommes ravis de vous compter parmi nous. Votre compte a été créé avec succès.</p>");
        emailBodyBuilder.append("<p><strong>Nom d'utilisateur :</strong> ").append(utilisateur.getEmail()).append("</p>");
        emailBodyBuilder.append("<p><strong>Mot de Passe :</strong> ").append(rawPassword).append("</p>");
        emailBodyBuilder.append("<p>Pour commencer, veuillez vous connecter à votre compte en utilisant vos identifiants.</p>");
        emailBodyBuilder.append("<p>Si vous avez des questions, n'hésitez pas à contacter notre support.</p>");
        emailBodyBuilder.append("</div>");
        emailBodyBuilder.append("<div class=\"footer\">");
        emailBodyBuilder.append("<p>L'équipe <strong>ODC</strong></p>");
        emailBodyBuilder.append("<p>Ceci est un email automatisé. Merci de ne pas y répondre.</p>");
        emailBodyBuilder.append("</div>");
        emailBodyBuilder.append("</div>");
        emailBodyBuilder.append("</body>");
        emailBodyBuilder.append("</html>");

        String emailBody = emailBodyBuilder.toString();
        emailService.sendSimpleEmail(utilisateur.getEmail(), "Confirmation de création de compte", emailBody);


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
                (Entite) utilisateur.getEntite()
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
                    p.setGenre(utilisateur.getGenre());

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
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Votre id n'existe pas"));
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
                    throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Le nouveau mot de passe ne peut pas être le même que l'ancien.");
                }
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "L'ancien mot de passe est incorrect.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur avec cet email n'existe pas.");
        }
    }



}
