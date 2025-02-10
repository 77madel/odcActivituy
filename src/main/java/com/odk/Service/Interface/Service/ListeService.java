package com.odk.Service.Interface.Service;

import com.odk.Entity.Liste;
import com.odk.Entity.Utilisateur;
import com.odk.Repository.ListeRepository;
import com.odk.Repository.ParticipantRepository;
import com.odk.Service.Interface.CrudService;
import com.odk.dto.ListeDTO;
import com.odk.dto.ListeMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ListeService implements CrudService<Liste, Long> {

    private ListeRepository listeRepository;
    private ParticipantRepository participantRepository;

    @Override
    public Liste add(Liste liste) {
        return null;
    }

    public List<ListeDTO> getAllListes() {
        return listeRepository.findAll().stream()
                .map(ListeDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<ListeDTO> getFindById(Long id) {
        return listeRepository.findById(id)
                .map(ListeDTO::new); // Convertir en DTO
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
    @Transactional
    public void delete(Long id) {
        // Supprime d'abord les participants liés
        participantRepository.deleteByListeId(id);

        Optional<Liste> listeOptional = listeRepository.findById(id);
        listeOptional.ifPresent(liste -> listeRepository.deleteById(id));

    }
}
