package com.odk.Auth;

import com.odk.Entity.Role;
import com.odk.Entity.Utilisateur;
import com.odk.Repository.UtilisateurRepository;
import com.odk.Service.Interface.Service.UtilisateurService;
import com.odk.dto.AuthentificationDTO;
import com.odk.securityConfig.JwtService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Slf4j
@Transactional
public class Login {

    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private UtilisateurRepository utilisateurRepository;
    private UtilisateurService utilisateurService;
    private JwtService jwtService;
    //private JwtEncoder jwtEncoder;

    @PostMapping("/login")
    /*public ResponseEntity<AuthentificationDTO> login(@RequestBody AuthentificationDTO authentificationDTO) {
        // Authentifier l'utilisateur
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authentificationDTO.getUsername(), authentificationDTO.getPassword())
        );

        // Si l'authentification réussit, définir l'utilisateur dans le contexte de sécurité
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Récupérer l'utilisateur à partir de son email
        Utilisateur utilisateur = utilisateurRepository.findByEmail(authentificationDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec cet email."));

        // Générer un token JWT
        String jwtToken = jwtAuthFilter.(authentication, utilisateur, jwtEncoder);
        System.out.println("Token généré : " + jwtToken); // Debug: afficher le token

        // Construire l'objet de réponse
        AuthentificationDTO authentificationDTO1 = new AuthentificationDTO();
        authentificationDTO1.setUsername(utilisateur.getEmail()); // Utiliser l'email de l'utilisateur
        authentificationDTO1.setToken(jwtToken); // Retourner le token JWT
        authentificationDTO1.setRole(utilisateur.getRole()); // Ajouter le rôle de l'utilisateur
        authentificationDTO1.setNom(utilisateur.getNom()); // Ajouter le nom de l'utilisateur
        authentificationDTO1.setPrenom(utilisateur.getPrenom()); // Ajouter le prénom de l'utilisateur

        return new ResponseEntity<>(authentificationDTO1, HttpStatus.OK);
    }*/

    public ResponseEntity<?> connexion(@RequestBody AuthentificationDTO authentificationDTO) {
        if (authentificationDTO.getUsername() == null || authentificationDTO.getPassword() == null) {
            return ResponseEntity.badRequest().body("Nom d'utilisateur et mot de passe requis"); // Réponse 400
        }

        try {
            final Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authentificationDTO.getUsername(), authentificationDTO.getPassword())
            );

            if (authenticate.isAuthenticated()) {
                Map<String, String> token = this.jwtService.generate(authentificationDTO.getUsername());
                return ResponseEntity.ok(token);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Échec de l'authentification");
            }
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nom d'utilisateur ou mot de passe incorrect");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur de connexion: " + e.getMessage());
        }
    }






    // 3. Déconnexion - Invalider le token JWT si utilisé
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        SecurityContextHolder.clearContext();  // Efface le contexte de sécurité de Spring Security
        return new ResponseEntity<>("Déconnecté avec succès.", HttpStatus.OK);
    }

    // Exemple de méthode pour envoyer un email (à implémenter ou intégrer un service d'envoi d'email)
    private void sendEmailWithResetLink(String email, String resetPasswordLink) {
        // Utiliser un service SMTP comme JavaMail, SendGrid, ou autre API pour envoyer l'email avec le lien
    }
}
