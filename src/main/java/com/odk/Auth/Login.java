package com.odk.Auth;

import com.odk.Entity.Utilisateur;
import com.odk.Repository.UtilisateurRepository;
import com.odk.dto.AuthentificationDTO;
import com.odk.securityConfig.JwtAuthFilter;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Slf4j
@Transactional
public class Login {

    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private UtilisateurRepository utilisateurRepository;
    private JwtAuthFilter jwtAuthFilter;
    private JwtEncoder jwtEncoder;

    @PostMapping("/login")
    public ResponseEntity<AuthentificationDTO> login(@RequestBody AuthentificationDTO authentificationDTO) {
        // Utilisation de l'authentication Manager pour authentifier l'utilisateur
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authentificationDTO.getUsername(), authentificationDTO.getPassword())
        );

        // Si l'authentification réussit, définir l'utilisateur dans le contexte de sécurité
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 2. Récupérer l'utilisateur à partir de son email
        Utilisateur utilisateur = utilisateurRepository.findByEmail(authentificationDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec cet email."));

        // 3. Générer un token JWT avec les informations de l'utilisateur et de l'organisation
        String jwtToken = jwtAuthFilter.generateToken(authentication, utilisateur, jwtEncoder);

        // Construire l'objet de réponse
        AuthentificationDTO authentificationDTO1 = new AuthentificationDTO();
        authentificationDTO1.setUsername(authentificationDTO1.getUsername());
        authentificationDTO1.setToken(jwtToken);  // Retourner le token JWT

        return new ResponseEntity<>(authentificationDTO1, HttpStatus.OK);
    }

    // 2. Mot de passe oublié - Envoi d'un lien de réinitialisation
    @PostMapping("/password/forgot")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) throws MessagingException {
        Utilisateur user = utilisateurRepository.findByEmail(email)
                .orElse(null);
        if (user == null) {
            return new ResponseEntity<>("Utilisateur non trouvé.", HttpStatus.NOT_FOUND);
        }

//        String resetToken = UUID.randomUUID().toString(); // Générer un token unique
//        user.setResetToken(resetToken);
//        user.setTokenExpiry(LocalDateTime.now().plusHours(1)); // Expiration du token dans 1 heure
//        utilisateurRepository.save(user); // Enregistrer le token dans la base de données

//        // Créer un lien de réinitialisation
//        String resetLink = "http://localhost:4200/reset-password?token=" + resetToken;
//
//        // Envoyer l'email
//        emailService.sendEmailWithResetLink(email, resetLink);

        return new ResponseEntity<>("Lien de réinitialisation envoyé.", HttpStatus.OK);
    }

    // Réinitialisation du mot de passe
//    @PostMapping("/reset-password")
//    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
//        Utilisateur user = utilisateurRepository.findByResetToken(token);
//        if (user == null || user.getTokenExpiry().isBefore(LocalDateTime.now())) {
//            return new ResponseEntity<>("Token invalide ou expiré.", HttpStatus.BAD_REQUEST);
//        }
//
//        // Ensure the firebaseUid is not null or empty
//        if (user.getFirebaseUid() == null || user.getFirebaseUid().isEmpty()) {
//            return new ResponseEntity<>("Firebase UID est manquant.", HttpStatus.BAD_REQUEST);
//        }
//
//        user.setPassword(passwordEncoder.encode(newPassword)); // Encoder le nouveau mot de passe
//        user.setResetToken(null); // Réinitialiser le token
//        user.setTokenExpiry(null); // Réinitialiser l'expiration
//        utilisateurRepository.save(user);
//
//        try {
//            UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(user.getFirebaseUid())
//                    .setPassword(newPassword);
//            FirebaseAuth.getInstance().updateUser(request);
//        } catch (FirebaseAuthException e) {
//            return new ResponseEntity<>("Erreur lors de la mise à jour du mot de passe Firebase.", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//        return new ResponseEntity<>("Mot de passe réinitialisé avec succès.", HttpStatus.OK);
//    }

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
