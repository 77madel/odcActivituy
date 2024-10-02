package com.odk.Service.Interface.Service;

import com.odk.Entity.Reponse;
import com.odk.Repository.ReponseRepository;
import com.odk.Service.Interface.CrudService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReponseService implements CrudService<Reponse, Long> {

    private ReponseRepository reponseRepository;

    @Override
    public Reponse add(Reponse reponse) {
        return reponseRepository.save(reponse);
    }

    @Override
    public List<Reponse> List() {
        return reponseRepository.findAll();
    }

    @Override
    public Optional<Reponse> findById(Long id) {
        return reponseRepository.findById(id);
    }

    @Override
    public Reponse update(Reponse reponse, Long id) {
        Optional<Reponse> optionalReponse = reponseRepository.findById(id);
        if (optionalReponse.isPresent()) {
            Reponse updateReponse = optionalReponse.get();
            updateReponse.setReponse(reponse.getReponse());
            updateReponse.setCorrect(reponse.getCorrect());
            return reponseRepository.save(updateReponse);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<Reponse> optionalReponse = reponseRepository.findById(id);
        optionalReponse.ifPresent(reponse -> reponseRepository.delete(reponse));
    }
}
