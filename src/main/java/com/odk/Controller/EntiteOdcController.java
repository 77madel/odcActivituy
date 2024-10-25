package com.odk.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.odk.Entity.Entite;
import com.odk.Entity.Utilisateur;
import com.odk.Service.Interface.Service.EntiteOdcService;
import com.odk.Service.Interface.Service.FileStorage;
import com.odk.Service.Interface.Service.UtilisateurService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/entite")
@AllArgsConstructor
public class EntiteOdcController {

    private ObjectMapper objectMapper;
    private EntiteOdcService entiteOdcService;
    private FileStorage fileStorage;
    private UtilisateurService utilisateurService;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Entite> ajout(
            @RequestPart("entiteOdc") String entiteOdcJson,
            @RequestPart("logo") MultipartFile logo,
            @RequestParam("utilisateurId") Long utilisateurId) {

        try {
            // Convertir le JSON en objet Entite
            Entite entite = objectMapper.readValue(entiteOdcJson, Entite.class);

            // Sauvegarder le fichier image
            String imagePath = fileStorage.saveImage(logo);
            entite.setLogo(imagePath);

            // Récupérer l'utilisateur par ID
            Optional<Utilisateur> utilisateurOpt = utilisateurService.findById(utilisateurId);
            // Vérifier si l'utilisateur est présent
            if (utilisateurOpt.isPresent()) {
                Utilisateur utilisateur = utilisateurOpt.get();

                // Vérifier que l'utilisateur a le rôle de "Personnel"
                if (utilisateur.getRole().getNom().equals("Personnel")) {
                    entite.setResponsable(utilisateur);
                } else {
                    throw new RuntimeException("Seuls les utilisateurs ayant le rôle 'Personnel' peuvent être responsables.");
                }
            } else {
                throw new RuntimeException("Utilisateur non trouvé avec l'ID : " + utilisateurId);
            }

            // Ajouter l'entité
            Entite createdFormation = entiteOdcService.add(entite);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFormation);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body(null);  // Erreur de conversion JSON
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);  // Erreurs générales
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Entite> ListerEntite(){
        return entiteOdcService.List();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Entite> getEnitteParId(@PathVariable Long id){
        return entiteOdcService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Entite Modifier(@PathVariable Long id, @RequestBody Entite entiteOdc ){
        return entiteOdcService.update(entiteOdc,id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  supprimer(@PathVariable Long id){
        entiteOdcService.delete(id);
    }

    @GetMapping("/image/{nomImage}")
    public ResponseEntity<Resource> getImage(@PathVariable String nomImage) {
        try {
            // Chemin complet vers l'image dans le dossier "images"
            String imagePath = "images/" + nomImage; // Assurez-vous que ce chemin est correct
            System.out.println("Trying to access image at path: " + imagePath); // Log du chemin de l'image

            FileSystemResource resource = new FileSystemResource(imagePath);

            // Vérifiez si le fichier existe
            if (resource.exists()) {
                System.out.println("Image found: " + imagePath); // Log si l'image est trouvée
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + nomImage); // Changez "attachment" à "inline" pour afficher l'image
                return ResponseEntity.ok()
                        .headers(headers)
                        .body((Resource) resource); // Pas besoin de cast ici
            } else {
                System.out.println("Image not found: " + imagePath); // Log si l'image n'est pas trouvée
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            System.err.println("Error occurred while fetching the image: " + e.getMessage()); // Log d'erreur
            e.printStackTrace(); // Affiche la trace de l'erreur pour plus de détails
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("get/{id}")
    public Long countActivitiesByEntite(@PathVariable Long id) {
        return entiteOdcService.getCountOfActivitiesByEntiteId(id);
    }



}
