package com.odk.Service.Interface.Service;

import com.odk.Entity.Role;
import com.odk.Entity.SuperAdmin;
import com.odk.Entity.Utilisateur;
import com.odk.Repository.RoleRepository;
import com.odk.Repository.SuperAdminRepository;
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
public class SuperAdminService implements CrudService<SuperAdmin, Long> {

    private final SuperAdminRepository superAdminRepository;
    private UtilisateurRepository utilisateurRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    @Override
    public SuperAdmin add(SuperAdmin superAdmin) {
        if(!UtilService.isValidEmail(superAdmin.getEmail())) {
            throw new RuntimeException("Votre mail est invalide");
        }

        Optional<Utilisateur> utilisateur = this.utilisateurRepository.findByEmail(superAdmin.getEmail());
        if(utilisateur.isPresent()) {
            throw new RuntimeException("Votre mail est déjà utilisé");
        }

        // Définir un mot de passe
        String defaultPassword = "motdepasse123";
        String encodePassword = passwordEncoder.encode(superAdmin.getPassword() != null ? superAdmin.getPassword() : defaultPassword);
        superAdmin.setPassword(encodePassword);

        // Vérifier si le rôle "Participant" existe, sinon le créer et sauvegarder
        Role role = roleRepository.findByNom("SuperAdmin").orElseGet(() -> {
            Role newRole = new Role();
            newRole.setNom("SuperAdmin");
            return roleRepository.save(newRole);  // Sauvegarder le rôle avant de l'associer
        });

        superAdmin.setRole(role);
        return superAdminRepository.save(superAdmin);
    }

    @Override
    public List<SuperAdmin> List() {
        return superAdminRepository.findAll();
    }

    @Override
    public Optional<SuperAdmin> findById(Long id) {
        return superAdminRepository.findById(id);
    }

    @Override
    public SuperAdmin update(SuperAdmin superAdmin, Long id) {
        Optional<SuperAdmin> optionalSuperAdmin = superAdminRepository.findById(id);
        if(optionalSuperAdmin.isPresent()) {
            SuperAdmin superAdminToUpdate = optionalSuperAdmin.get();
            superAdminToUpdate.setNom(superAdmin.getNom());
            superAdminToUpdate.setEmail(superAdmin.getEmail());
            superAdminToUpdate.setPrenom(superAdmin.getPrenom());
            superAdminToUpdate.setPhone(superAdmin.getPhone());
            return superAdminRepository.save(superAdminToUpdate);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<SuperAdmin> optionalSuperAdmin = superAdminRepository.findById(id);
        optionalSuperAdmin.ifPresent(superAdminRepository::delete);
    }
}
