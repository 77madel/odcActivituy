package com.odk.Controller;

import com.odk.Repository.ActiviteParticipantRepository;
import com.odk.Service.Interface.Service.ListeService;
import com.odk.dto.ListeDTO;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/liste")
public class ListeController {

    private ListeService listeService;
    private ActiviteParticipantRepository activiteParticipantRepository;
    @GetMapping
    public List<ListeDTO> getAllListes() {
        return listeService.getAllListes();
    }

    @GetMapping("/{id}")
    public Optional<ListeDTO> getListeById(@PathVariable Long id) {
        return listeService.getFindById(id);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deleteListe(@PathVariable Long id) {
        // Supprimer les liens avec activite_participant
        activiteParticipantRepository.deleteByParticipantId(id);

        listeService.delete(id);
    }



}
