package com.odk.Controller;

import com.odk.Entity.Etape;
import com.odk.Service.Interface.Service.EtapeService;
import com.odk.Service.Interface.Service.ImportService;
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


    @PostMapping("/upload-liste-debut/{etapeId}")
    public ResponseEntity<Etape> uploadListeDebut(@PathVariable Long etapeId, @RequestParam("file") MultipartFile file) {
        // Debugging pour vérifier si la méthode est appelée
        System.out.println("Requête reçue pour l'étape ID: " + etapeId);
        System.out.println("Fichier reçu: " + (file != null ? file.getOriginalFilename() : "Aucun fichier"));

        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }


        try {
            List<String> participants = excelService.lireParticipantsDepuisExcel(file);
            Optional<Etape> optionalEtape = etapeService.findById(etapeId);

            if (optionalEtape.isPresent()) {
                Etape etape = optionalEtape.get();
                etape.getListeDebut().addAll(participants);
                Etape updatedEtape = etapeService.add(etape);
                return new ResponseEntity<>(updatedEtape, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/simple-upload")
    public ResponseEntity<String> simpleUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Aucun fichier reçu");
        }
        return ResponseEntity.ok("Fichier reçu : " + file.getOriginalFilename());
    }


}
