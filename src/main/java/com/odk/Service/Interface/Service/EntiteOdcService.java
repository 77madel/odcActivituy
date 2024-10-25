package com.odk.Service.Interface.Service;

import com.odk.Entity.Entite;
import com.odk.Entity.Utilisateur;
import com.odk.Repository.EntiteOdcRepository;
import com.odk.Repository.UtilisateurRepository;
import com.odk.Service.Interface.CrudService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EntiteOdcService implements CrudService<Entite, Long> {

    private EntiteOdcRepository entiteOdcRepository;
    private UtilisateurRepository utilisateurRepository;
    @Override
    public Entite add(Entite entiteOdc) {
        return entiteOdcRepository.save(entiteOdc);
    }

    @Override
    public List<Entite> List() {
        return entiteOdcRepository.findAll();
    }

    @Override
    public Optional<Entite> findById(Long id) {
        return entiteOdcRepository.findById(id);
    }

    @Override
    public Entite update(Entite entity, Long id) {
        Optional<Entite> entiteOdc = entiteOdcRepository.findById(id);
        if (entiteOdc.isPresent()) {
            return entiteOdcRepository.save(entity);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<Entite> entiteOdc = entiteOdcRepository.findById(id);
        if (entiteOdc.isPresent()) {
            entiteOdcRepository.deleteById(id);
        }
    }

    public Long getCountOfActivitiesByEntiteId(Long entiteId) {
        return entiteOdcRepository.countActivitiesByEntiteId(entiteId);
    }

    public List<Utilisateur> findUtilisateursByRole(String roleName) {
        return utilisateurRepository.findByRoleNom(roleName);
    }

}
