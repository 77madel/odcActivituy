package com.odk.Service.Interface.Service;

import com.odk.Entity.Participant;
import com.odk.Entity.Role;
import com.odk.Entity.Utilisateur;
import com.odk.Entity.Vigile;
import com.odk.Repository.RoleRepository;
import com.odk.Repository.UtilisateurRepository;
import com.odk.Repository.VigileRepository;
import com.odk.Service.Interface.CrudService;
import com.odk.Utils.UtilService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class VigileService implements CrudService<Vigile, Long> {

    private VigileRepository vigileRepository;
    private UtilisateurRepository utilisateurRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private RoleService roleService;
    private RoleRepository repositoryRole;

    @Override
    public Vigile add(Vigile vigile) {
        if(!UtilService.isValidEmail(vigile.getEmail())) {
            throw new RuntimeException("Votre mail est invalide");
        }

        Optional<Utilisateur> utilisateur = this.utilisateurRepository.findByEmail(vigile.getEmail());
        if(utilisateur.isPresent()) {
            throw new RuntimeException("Votre mail est déjà utilisé");
        }

        // Définir un mot de passe
        String defaultPassword = "motdepasse123";
        String encodePassword = passwordEncoder.encode(vigile.getPassword() != null ? vigile.getPassword() : defaultPassword);
        vigile.setPassword(encodePassword);

        // Vérifier si le rôle "Participant" existe, sinon le créer et sauvegarder
        Role role = roleRepository.findByNom("Vigile").orElseGet(() -> {
            Role newRole = new Role();
            newRole.setNom("Vigile");
            return roleRepository.save(newRole);  // Sauvegarder le rôle avant de l'associer
        });

        vigile.setRole(role);
        return vigileRepository.save(vigile);
    }

    @Override
    public List<Vigile> List() {
        return vigileRepository.findVigiles();
    }

    @Override
    public Optional<Vigile> findById(Long id) {
        return vigileRepository.findById(id);
    }

    @Override
    public Vigile update(Vigile vigile, Long id) {
        //Optional<Vigile> optionalVigile = vigileRepository.findById(id);
        return vigileRepository.findById(id).map(
                p -> {
                    p.setNom(vigile.getNom());
                    p.setEmail(vigile.getEmail());
                    p.setPrenom(vigile.getPrenom());
                    p.setPhone(vigile.getPhone());
                    return vigileRepository.save(p);
                }).orElseThrow(()-> new RuntimeException("Votre id n'existe pas"));
    }

    @Override
    public void delete(Long id) {
        Optional<Vigile> optionalParticipant = vigileRepository.findById(id);
        optionalParticipant.ifPresent(vigileRepository::delete);
    }
}
