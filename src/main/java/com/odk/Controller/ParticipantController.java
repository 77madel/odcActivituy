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


    @PostMapping("/ajout")
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


    @GetMapping("/liste")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipantDTO> ListerEntite(){
        return participantService.listParticipant();
    }

    @GetMapping("/liste/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Participant> getParticipantParId(@PathVariable Long id){
        return participantService.findById(id);
    }

    @PatchMapping("/modifier/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Participant Modifier(@PathVariable Long id, @RequestBody Participant participant ){
        return participantService.update(participant,id);
    }

    @DeleteMapping("/supprimer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void  supprimer(@PathVariable Long id){
        participantService.delete(id);
    }

    @PostMapping("check-in/{id}")
    public Participant checking(@PathVariable Long id){
        return participantService.checkInParticipant(id);

    }

    @GetMapping("/check")
    public ResponseEntity<String> checkParticipant(@RequestParam String email, @RequestParam String phone) {
        boolean isBlacklisted = blackListService.isParticipantBlacklisted(email, phone);
        if (isBlacklisted) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Participant est blacklister");
        }
        return ResponseEntity.ok("Participant n'est pas blacklister");
    }

}
