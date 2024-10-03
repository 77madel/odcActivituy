package com.odk.Service.Interface.Service;

import com.odk.Entity.Chapitre;
import com.odk.Repository.ChapitreRepository;
import com.odk.Service.Interface.CrudService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ChapitreService implements CrudService<Chapitre, Long> {

    private ChapitreRepository chapitreRepository;

    @Override
    public Chapitre add(Chapitre chapitre) {
        return chapitreRepository.save(chapitre);
    }

    @Override
    public List<Chapitre> List() {
        return chapitreRepository.findAll();
    }

    @Override
    public Optional<Chapitre> findById(Long id) {
        return chapitreRepository.findById(id);
    }

    @Override
    public Chapitre update(Chapitre chapitre, Long id) {
        Optional<Chapitre> chapitreOptional = chapitreRepository.findById(id);
        if (chapitreOptional.isPresent()) {
            Chapitre chapitreToUpdate = chapitreOptional.get();
            chapitreToUpdate.setNom(chapitre.getNom());
//            chapitreToUpdate.setCours(chapitre.getCours());
            return chapitreRepository.save(chapitreToUpdate);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
     Optional<Chapitre> chapitreOptional = chapitreRepository.findById(id);
     chapitreOptional.ifPresent(chapitreRepository::delete);
    }
}
