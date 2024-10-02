package com.odk.Service.Interface.Service;

import com.odk.Entity.Cours;
import com.odk.Repository.CoursRepository;
import com.odk.Service.Interface.CrudService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CoursService implements CrudService<Cours, Long> {

    private CoursRepository coursRepository;

    @Override
    public Cours add(Cours cours) {
        return coursRepository.save(cours);
    }

    @Override
    public List<Cours> List() {
        return coursRepository.findAll();
    }

    @Override
    public Optional<Cours> findById(Long id) {
        return coursRepository.findById(id);
    }

    @Override
    public Cours update(Cours cours, Long id) {
        Optional<Cours> coursOptional = coursRepository.findById(id);
        if(coursOptional.isPresent()) {
            Cours existingCours = coursOptional.get();
            existingCours.setTitre(cours.getTitre());
//            existingCours.setActivite(cours.getActivite());
//            existingCours.setChapitres(cours.getChapitres());
            return coursRepository.save(existingCours);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<Cours> coursOptional = coursRepository.findById(id);
        coursOptional.ifPresent(cours -> coursRepository.delete(cours));
    }
}
