package com.odk.Service.Interface.Service;

import com.odk.Entity.Role;
import com.odk.Repository.RoleRepository;
import com.odk.Service.Interface.CrudService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleService implements CrudService<Role, Long> {

    private RoleRepository roleRepository;

    @Override
    public Role add(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> List() {
        return roleRepository.findAll() ;
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Role update(Role role, Long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isPresent()) {
            Role existingRole = optionalRole.get();
            // Mise à jour des champs spécifiques
            existingRole.setNom(role.getNom());
            return roleRepository.save(existingRole);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        optionalRole.ifPresent(role -> roleRepository.delete(role));
    }

}
