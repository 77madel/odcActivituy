package com.odk.Service.Interface.Service;

import com.odk.Entity.Liste;
import com.odk.Repository.ListeRepository;
import com.odk.Service.Interface.CrudService;
import com.odk.dto.ListeDTO;
import com.odk.dto.ListeMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ListeService implements CrudService<Liste, Long> {

    private ListeRepository listeRepository;
    private ListeMapper listeMapper;

    @Override
    public Liste add(Liste liste) {
        return null;
    }

    public List<ListeDTO> getAllListes() {
        return listeRepository.findAll().stream()
                .map(listeMapper::toListeDTO)
                .collect(Collectors.toList());
    }

    public Optional<Liste> getFindById(Long id) {  // Retourner Optional<Liste> ici
        return listeRepository.findById(id);
    }

    @Override
    public List<Liste> List() {

        return listeRepository.findAll();
    }

    @Override
    public Optional<Liste> findById(Long id) {
        return listeRepository.findById(id);
    }

    @Override
    public Liste update(Liste entity, Long aLong) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
