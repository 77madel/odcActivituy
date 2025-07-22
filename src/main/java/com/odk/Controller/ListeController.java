package com.odk.Controller;

import com.odk.Repository.ActiviteParticipantRepository;
import com.odk.Service.Interface.Service.ListeService;
import com.odk.dto.ListeDTO;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('PERSONNEL') or hasRole('SUPERADMIN')")
    public List<ListeDTO> getAllListes() {
        return listeService.getAllListes();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PERSONNEL') or hasRole('SUPERADMIN')")
    public Optional<ListeDTO> getListeById(@PathVariable Long id) {
        return listeService.getFindById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PERSONNEL')")
    @Transactional
    public void deleteListe(@PathVariable Long id) {
        // Supprimer les liens avec activite_participant
        activiteParticipantRepository.deleteByParticipantId(id);

        listeService.delete(id);
    }



}
