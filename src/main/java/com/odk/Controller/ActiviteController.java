package com.odk.Controller;

import com.odk.Entity.Activite;
import com.odk.Entity.Etape;
import com.odk.Service.Interface.Service.ActiviteService;
import com.odk.dto.ActiviteDTO;
import com.odk.dto.ParticipantDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/activite")
public class ActiviteController {

    private ActiviteService activiteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Activite ajouter(@RequestBody Activite activite) {
        try {
            return activiteService.add(activite);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de l'ajout de l'activité", e);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ActiviteDTO> listerActivite() {
        return activiteService.List().stream()
                .map(activite -> {
                    List<Etape> etapes = activite.getEtape();
                    List<ParticipantDTO> listeDebutDTO = new ArrayList<>();
                    List<ParticipantDTO> listeResultatDTO = new ArrayList<>();

                    if (etapes != null) {
                        for (Etape etape : etapes) {
                            // Récupération de listeDebut
                            listeDebutDTO.addAll(etape.getListeDebut().stream()
                                    .map(participant -> new ParticipantDTO(participant.getId(), participant.getNom()))
                                    .collect(Collectors.toList()));

                            // Récupération de listeResultat
                            listeResultatDTO.addAll(etape.getListeResultat().stream()
                                    .map(participant -> new ParticipantDTO(participant.getId(), participant.getNom()))
                                    .collect(Collectors.toList()));

                            // Log de débogage
                            System.out.println("Étape: " + etape.getNom() + " Liste Résultat: " + listeResultatDTO);
                        }
                    }

                    return new ActiviteDTO(
                            activite.getId(),
                            activite.getNom(),
                            activite.getTitre(),
                            activite.getDateDebut(),
                            activite.getDateFin(),
                            activite.getLieu(),
                            activite.getDescription(),
                            activite.getObjectifParticipation(),
                            etapes != null && !etapes.isEmpty() ? etapes.get(0) : null, // Prend la première étape
                            activite.getEntite(),
                            activite.getTypeActivite(),
                            listeDebutDTO,
                            listeResultatDTO
                    );
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Activite> getActiviteParId(@PathVariable Long id) {
        try {
            return activiteService.findById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la récupération de l'activité par ID", e);
        }
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Activite modifier(@PathVariable Long id, @RequestBody Activite activite) {
        try {
            return activiteService.update(activite, id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la modification de l'activité", e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimer(@PathVariable Long id) {
        try {

            activiteService.delete(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la suppression de l'activité", e);
        }
    }
}

