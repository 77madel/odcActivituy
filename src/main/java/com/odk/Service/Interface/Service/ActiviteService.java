package com.odk.Service.Interface.Service;

import com.odk.Entity.Activite;
import com.odk.Repository.ActiviteRepository;
import com.odk.Service.Interface.CrudService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ActiviteService implements CrudService<Activite, Long> {

    private ActiviteRepository activiteRepository;

    @Override
    public Activite add(Activite entity) {
        return activiteRepository.save(entity);
    }

    @Override
    public List<Activite> List() {
        return activiteRepository.findAll();
    }

    @Override
    public Optional<Activite> findById(Long id) {
        return activiteRepository.findById(id);
    }

    @Override
    public Activite update(Activite activite, Long id) {
        Optional<Activite> activiteOptional = activiteRepository.findById(id);
        if (activiteOptional.isPresent()) {
          return  activiteRepository.save(activite);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<Activite> activiteOptional = activiteRepository.findById(id);
        activiteOptional.ifPresent(activite -> activiteRepository.delete(activite));
    }
}
