package com.odk.Service.Interface.Service;

import com.odk.Entity.Participant;
import com.odk.Entity.Personnel;
import com.odk.Entity.Role;
import com.odk.Entity.Utilisateur;
import com.odk.Repository.PersonnelRepository;
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
public class PersonnelService implements CrudService<Personnel, Long> {

    private PersonnelRepository personnelRepository;
    private UtilisateurRepository utilisateurRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    @Override
    public Personnel add(Personnel personnel) {
        if(!UtilService.isValidEmail(personnel.getEmail())) {
            throw new RuntimeException("Votre mail est invalide");
        }

        Optional<Utilisateur> utilisateur = this.utilisateurRepository.findByEmail(personnel.getEmail());
        if(utilisateur.isPresent()) {
            throw new RuntimeException("Votre mail est déjà utilisé");
        }

        // Définir un mot de passe
        String defaultPassword = "motdepasse123";
        String encodePassword = passwordEncoder.encode(personnel.getPassword() != null ? personnel.getPassword() : defaultPassword);
        personnel.setPassword(encodePassword);

        // Vérifier si le rôle "Participant" existe, sinon le créer et sauvegarder
        Role role = roleRepository.findByNom("Personnel").orElseGet(() -> {
            Role newRole = new Role();
            newRole.setNom("Personnel");
            return roleRepository.save(newRole);  // Sauvegarder le rôle avant de l'associer
        });

        personnel.setRole(role);
        return personnelRepository.save(personnel);
    }

    @Override
    public List<Personnel> List() {
        return personnelRepository.findPersonnels();
    }

    @Override
    public Optional<Personnel> findById(Long id) {
        return personnelRepository.findById(id);
    }

    @Override
    public Personnel update(Personnel personnel, Long id) {
        //Optional<Personnel> optionalPersonnel = personnelRepository.findById(id);
        return personnelRepository.findById(id).map(
                p -> {
                    p.setNom(personnel.getNom());
                    p.setEmail(personnel.getEmail());
                    p.setPrenom(personnel.getPrenom());
                    p.setPhone(personnel.getPhone());
                    p.setPassword(passwordEncoder.encode(personnel.getPassword()));
                    p.setEntite(personnel.getEntite());
                    p.setRole(personnel.getRole());
                    return personnelRepository.save(p);
                }).orElseThrow(()-> new RuntimeException("Votre id n'existe pas"));
        /*if (optionalPersonnel.isPresent()) {
            Personnel personnelUpdate = optionalPersonnel.get();
            personnelUpdate.setNom(personnelUpdate.getNom());
            personnelUpdate.setEmail(personnelUpdate.getEmail());
            personnelUpdate.setPrenom(personnelUpdate.getPrenom());
            personnelUpdate.setPhone(personnelUpdate.getPhone());
            personnelUpdate.setPassword(passwordEncoder.encode(personnelUpdate.getPassword()));
            personnelUpdate.setEntite(personnelUpdate.getEntite());
            personnelUpdate.setRole(personnelUpdate.getRole());
            return personnelRepository.save(personnel);
        }*/

        //return null;

    }

    @Override
    public void delete(Long id) {
        Optional<Personnel> optionalPersonnel = personnelRepository.findById(id);
        optionalPersonnel.ifPresent(personnel -> personnelRepository.deleteById(id));
    }
}
