package com.odk.Controller;

import com.odk.Entity.Activite;
import com.odk.Entity.Etape;
import com.odk.Entity.Participant;
import com.odk.Repository.ActiviteRepository;
import com.odk.Repository.EtapeRepository;
import com.odk.Repository.ParticipantRepository;
import com.odk.Service.Interface.Service.EtapeService;
import com.odk.Service.Interface.Service.ImportService;
import com.odk.helper.ExcelHelper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/etape")
@AllArgsConstructor
public class EtapeController {

    private EtapeService etapeService;
    private ImportService excelService;
    private ParticipantRepository participantRepository;
    private ActiviteRepository activiteRepository;
    private EtapeRepository etapeRepository;


    @PostMapping("/ajout")
    @ResponseStatus(HttpStatus.CREATED)
    public Etape ajouter(@RequestBody Etape etape){
        return etapeService.add(etape);
    }

    @GetMapping("/liste")
    @ResponseStatus(HttpStatus.OK)
    public List<Etape> ListerEtape(){
        return etapeService.List();
    }

    @GetMapping("/liste/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Etape> getEtapeParId(@PathVariable Long id){
        return etapeService.findById(id);
    }

    @PutMapping("/modifier/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Etape Modifier(@PathVariable Long id, @RequestBody Etape etape ){
        return etapeService.update(etape,id);
    }

    @DeleteMapping("/supprimer/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  supprimer(@PathVariable Long id){
        etapeService.delete(id);
    }

    @PostMapping("/{etapeId}/add-debut")
    public String addParticipantToListeDebut(@PathVariable Long etapeId, @RequestParam String participant) {
        Etape etape = etapeRepository.findById(etapeId)
                .orElseThrow(() -> new RuntimeException("Étape non trouvée avec l'ID " + etapeId));

        etape.addParticipantToDebut(participant);
        etapeRepository.save(etape);

        return "Participant ajouté à la liste de début avec succès.";
    }

}
