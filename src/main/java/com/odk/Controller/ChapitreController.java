package com.odk.Controller;

import com.odk.Entity.Activite;
import com.odk.Entity.Chapitre;
import com.odk.Service.Interface.Service.ChapitreService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/chapitre")
public class ChapitreController {

    private ChapitreService chapitreService;


    @PostMapping("/ajout")
    @ResponseStatus(HttpStatus.CREATED)
    public Chapitre ajouter(@RequestBody Chapitre chapitre) {
        try {
            return chapitreService.add(chapitre);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de l'ajout du chapitre", e);
        }
    }

    @GetMapping("/liste")
    @ResponseStatus(HttpStatus.OK)
    public List<Chapitre> lister() {
        try {
            return chapitreService.List();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la récupération des chapitres", e);
        }
    }

    @GetMapping("/liste/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Chapitre> getActiviteParId(@PathVariable Long id) {
        try {
            return chapitreService.findById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la récupération de l'activité par ID", e);
        }
    }

    @PutMapping("/modifier/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Chapitre modifier(@PathVariable Long id, @RequestBody Chapitre chapitre) {
        try {
            return chapitreService.update(chapitre, id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la modification du chapitre", e);
        }
    }

    @DeleteMapping("/supprimer/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimer(@PathVariable Long id) {
        try {
            chapitreService.delete(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la suppression du chapitre", e);
        }
    }
}
