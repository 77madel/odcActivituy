package com.odk.Service.Interface.Service;

import com.odk.Entity.Salle;
import com.odk.Repository.SalleRepository;
import com.odk.Service.Interface.CrudService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SalleService implements CrudService<Salle, Long> {

    private SalleRepository salleRepository;

    @Override
    public Salle add(Salle salle)
    {
        return salleRepository.save(salle);
    }

    @Override
    public List<Salle> List() {
        return salleRepository.findAll();
    }

    @Override
    public Optional<Salle> findById(Long id) {
        return salleRepository.findById(id);
    }

    @Override
    public Salle update(Salle salle, Long id) {
        Optional<Salle> salleOptional = salleRepository.findById(id);
        if (salleOptional.isPresent()) {
            Salle salleUpdate = salleOptional.get();
            salleUpdate.setNom(salle.getNom());
            salleUpdate.setCapacite(salle.getCapacite());
            salleRepository.save(salleUpdate);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<Salle> salleOptional = salleRepository.findById(id);
        salleOptional.ifPresent(salleRepository::delete);

    }
}
