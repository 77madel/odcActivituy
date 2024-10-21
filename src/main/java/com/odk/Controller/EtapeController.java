package com.odk.Controller;

import com.odk.Entity.Etape;
import com.odk.Entity.ResponseMessage;
import com.odk.Repository.EtapeRepository;
import com.odk.Service.Interface.Service.EtapeService;
import com.odk.dto.EtapeDTO;
import com.odk.helper.ExcelHelper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/etape")
@AllArgsConstructor
public class EtapeController {

    private EtapeService etapeService;
    private EtapeRepository etapeRepository;


    @PostMapping("/ajout")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Etape> addEtape(@RequestBody Etape etape) {
        // Validation et ajout à la base de données
        Etape savedEtape = etapeRepository.save(etape);
        return ResponseEntity.ok(savedEtape);
    }

    @GetMapping("/liste")
    @ResponseStatus(HttpStatus.OK)
   /* public ResponseEntity<List<Etape>> getAllEtapes() {
        List<Etape> etapes = etapeService.List();

        // Vérification des données dans listeDebut
        for (Etape etape : etapes) {
            if (etape.getListeDebut() == null || etape.getListeDebut().isEmpty()) {
                System.out.println("ListeDebut est vide pour l'étape : " + etape.getNom());
            }
        }

        return new ResponseEntity<>(etapes, HttpStatus.OK);
    }*/

    public List<EtapeDTO> getAllEtapes() {
        return etapeService.getAllEtapes(); // Utilise le service pour récupérer les étapes sous forme de DTO
    }

//    @GetMapping("/liste/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public Optional<Etape> getEtapeParId(@PathVariable Long id){
//        return etapeService.findById(id);
//    }

    @PutMapping("/modifier/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Etape> Modifier(@PathVariable Long id, @RequestBody Etape etape ){

      Etape updateEtape =  etapeService.update(etape,id);
        return ResponseEntity.ok(updateEtape);
    }

    @DeleteMapping("/supprimer/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  supprimer(@PathVariable Long id){
        etapeService.delete(id);
    }

//    @PostMapping("/{id}/participants/upload")
//    public ResponseEntity<String> uploadParticipantsToEtape(@PathVariable Long id,
//                                                            @RequestParam("file") MultipartFile file,
//                                                            @RequestParam("liste") String liste) {
//        try {
//            boolean toListeDebut = "debut".equalsIgnoreCase(liste);
//            etapeService.(id, file, toListeDebut);
//            return ResponseEntity.ok("Participants ajoutés avec succès à l'étape.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Erreur lors de l'importation : " + e.getMessage());
//        }
//    }

    @PostMapping("/{id}/participants/upload")
    public ResponseEntity<?> uploadParticipants(@PathVariable Long id, @RequestParam("file") MultipartFile file, @RequestParam boolean toListeDebut) {
        try {
            etapeService.addParticipantsToEtape(id, file, toListeDebut);
            return ResponseEntity.ok(new ResponseMessage("Participants ajoutés avec succès"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(e.getMessage()));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("Erreur lors de l'importation du fichier"));
        }
    }


    @GetMapping("/liste/{id}")
    public ResponseEntity<List<EtapeDTO>> getEtape(@PathVariable Long id) {
        List<EtapeDTO> etapes = etapeService.getEtapeDTO(id);
        return new ResponseEntity<>(etapes, HttpStatus.OK);
    }

}
