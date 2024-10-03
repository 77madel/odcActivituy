package com.odk.Controller;

import com.odk.Entity.Participant;
import com.odk.Service.Interface.Service.ParticipantService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/participant")
public class ParticipantController {

    private ParticipantService participantService;

    @PostMapping("/ajout")
    @ResponseStatus(HttpStatus.CREATED)
    public Participant ajouter(@RequestBody Participant participant){
        return participantService.add(participant);
    }

    @GetMapping("/ListeParticipant")
    @ResponseStatus(HttpStatus.FOUND)
    public List<Participant> ListerEntite(){
        return participantService.List();
    }

    @GetMapping("/listeParticipant/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Optional<Participant> getParticipantParId(@PathVariable Long id){
        return participantService.findById(id);
    }

    @PutMapping("/modifier/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Participant Modifier(@PathVariable Long id, @RequestBody Participant participant ){
        return participantService.update(participant,id);
    }

    @DeleteMapping("/supprimer/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  supprimer(@PathVariable Long id){
        participantService.delete(id);
    }

}
