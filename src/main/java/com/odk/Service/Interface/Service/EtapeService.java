package com.odk.Service.Interface.Service;

import com.odk.Entity.Etape;
import com.odk.Repository.EtapeRepository;
import com.odk.Service.Interface.CrudService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EtapeService implements CrudService<Etape, Long> {

    private EtapeRepository etapeRepository;

    @Override
    public Etape add(Etape etape) {
        return etapeRepository.save(etape);
    }

    @Override
    public List<Etape> List() {
        return etapeRepository.findAll();
    }

    @Override
    public Optional<Etape> findById(Long id) {
        return etapeRepository.findById(id);
    }

    @Override
    public Etape update(Etape entity, Long id) {
        Optional<Etape> etape = etapeRepository.findById(id);
        if (etape.isPresent()) {
          return   etapeRepository.save(entity);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<Etape> optionalEtape = etapeRepository.findById(id);
        optionalEtape.ifPresent(etape -> etapeRepository.delete(etape));
    }
}
