package com.odk.Controller;

import com.odk.Entity.Activite;
import com.odk.Entity.ActiviteParticipant;
import com.odk.Entity.ActiviteParticipantKey;
import com.odk.Entity.Participant;
import com.odk.Repository.ActiviteParticipantRepository;
import com.odk.Repository.BlackListRepository;
import com.odk.Service.Interface.Service.BlackListService;
import com.odk.Service.Interface.Service.ParticipantService;
import com.odk.dto.ParticipantDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/participant")
public class ParticipantController {

    private ParticipantService participantService;
    private ActiviteParticipantRepository activiteParticipantRepository;
    private BlackListService blackListService;


    @PostMapping
    @PreAuthorize("hasRole('PERSONNEL')")
    @ResponseStatus(HttpStatus.CREATED)
    public Participant ajouter(@RequestBody Participant participant, Activite activite){
        Participant savedParticipant = participantService.addP(participant, activite.getId());
        // Créez la clé pour ActiviteParticipant
        ActiviteParticipantKey key = new ActiviteParticipantKey(activite.getId(), savedParticipant.getId());

        // Créez et enregistrez l'ActiviteParticipant
        ActiviteParticipant activiteParticipant = new ActiviteParticipant(key, activite, savedParticipant, LocalDate.now());
        activiteParticipantRepository.save(activiteParticipant);

        return savedParticipant;
    }


    @GetMapping
    @PreAuthorize("hasRole('PERSONNEL') or hasRole('SUPERADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipantDTO> ListerEntite(){
        return participantService.listParticipant();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PERSONNEL') or hasRole('SUPERADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Participant> getParticipantParId(@PathVariable Long id){
        return participantService.findById(id);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('PERSONNEL')")
    @ResponseStatus(HttpStatus.CREATED)
    public Participant Modifier(@PathVariable Long id, @RequestBody Participant participant ){
        return participantService.update(participant,id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PERSONNEL') or hasRole('SUPERADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void  supprimer(@PathVariable Long id){
        participantService.delete(id);
    }

    @PostMapping("check-in/{id}")
    public Participant checking(@PathVariable Long id){
        return participantService.checkInParticipant(id);

    }

    @GetMapping("/check")
    @PreAuthorize("hasRole('PERSONNEL') or hasRole('SUPERADMIN')")
    public ResponseEntity<String> checkParticipant(@RequestParam String email, @RequestParam String phone) {
        boolean isBlacklisted = blackListService.isParticipantBlacklisted(email, phone);
        if (isBlacklisted) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Participant est blacklister");
        }
        return ResponseEntity.ok("Participant n'est pas blacklister");
    }

}
