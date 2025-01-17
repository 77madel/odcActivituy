package com.odk.Entity;

import com.odk.Repository.RoleRepository;
import com.odk.Repository.UtilisateurRepository;
import com.odk.securityConfig.BcryptPassword;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private UtilisateurRepository utilisateurRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        // Vérifie si le rôle SUPERADMIN existe, sinon le crée
        Role superAdminRole = roleRepository.findByNom("SUPERADMIN")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setNom("SUPERADMIN");
                    return roleRepository.save(newRole);
                });

        // Vérifie si un utilisateur SUPERADMIN existe déjà pour éviter les doublons
        if (utilisateurRepository.findByEmail("admin@gmail.com").isEmpty()) {
            Utilisateur superAdmin = new Utilisateur();
            superAdmin.setNom("admin");
            superAdmin.setPrenom("admin");
            superAdmin.setPhone("78412541");  // Exemple de numéro de téléphone
            superAdmin.setGenre("Homme");  // Exemple de genre (ou "Femme" selon votre besoin)
            superAdmin.setEmail("admin@gmail.com");
            superAdmin.setPassword(passwordEncoder.encode("motdepasse123")); // Mot de passe encodé
            superAdmin.setRole(superAdminRole);

            // Sauvegarde l'utilisateur dans la base de données
            utilisateurRepository.save(superAdmin);

            System.out.println("Utilisateur SUPERADMIN créé avec succès !");
        } else {
            System.out.println("Utilisateur SUPERADMIN existe déjà !");
        }


        // Vérifie si le rôle SUPERADMIN existe, sinon le crée
        Role PersonnelROle = roleRepository.findByNom("PERSONNEL")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setNom("PERSONNEL");
                    return roleRepository.save(newRole);
                });

        // Vérifie si un utilisateur SUPERADMIN existe déjà pour éviter les doublons
        if (utilisateurRepository.findByEmail("madoumadeltitokone77@gmail.com").isEmpty()) {
            Utilisateur personnel = new Utilisateur();
            personnel.setNom("personnel");
            personnel.setPrenom("personnel");
            personnel.setPhone("78412541");  // Exemple de numéro de téléphone
            personnel.setGenre("Homme");  // Exemple de genre (ou "Femme" selon votre besoin)
            personnel.setEmail("madoumadeltitokone77@gmail.com");
            personnel.setPassword(passwordEncoder.encode("motdepasse123")); // Mot de passe encodé
            personnel.setRole(PersonnelROle);
           // personnel.setEntite();

            // Sauvegarde l'utilisateur dans la base de données
            utilisateurRepository.save(personnel);

            System.out.println("Utilisateur Personnel créé avec succès !");
        } else {
            System.out.println("Utilisateur Personnel existe déjà !");
        }

    }
}
