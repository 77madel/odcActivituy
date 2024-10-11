package com.odk.Service.Interface.Service;

import com.odk.Entity.Critere;
import com.odk.Repository.CritereRepository;
import com.odk.Service.Interface.CrudService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CritereService implements CrudService<Critere, Long> {

    private CritereRepository critereRepository;

    @Override
    public Critere add(Critere critere) {
        return critereRepository.save(critere);
    }

    @Override
    public List<Critere> List() {
        return critereRepository.findAll();
    }

    @Override
    public Optional<Critere> findById(Long id) {
        return critereRepository.findById(id);
    }

    @Override
    public Critere update(Critere critere, Long id) {
        Optional<Critere> critereOptional = critereRepository.findById(id);
        if (critereOptional.isPresent()) {
            Critere critereUpdate = critereOptional.get();
            critereUpdate.setLibelle(critere.getLibelle());
            critereUpdate.setIntutile(critere.getIntutile());
            critereUpdate.setPoint(critere.getPoint());
            critereRepository.save(critereUpdate);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<Critere> critereOptional = critereRepository.findById(id);
        critereOptional.ifPresent(critereRepository::delete);

    }
}
