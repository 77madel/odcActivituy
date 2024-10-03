package com.odk.Controller;

import com.odk.Entity.Reponse;
import com.odk.Service.Interface.Service.ReponseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reponse")
@AllArgsConstructor
public class ReponseController {

    private ReponseService reponseService;

    @PostMapping("/ajout")
    @ResponseStatus(HttpStatus.CREATED)
    public Reponse ajouter(@RequestBody Reponse reponse) {
        try {
            return reponseService.add(reponse);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de l'ajout d'une reponse", e);
        }
    }

    @GetMapping("/listeReponse")
    @ResponseStatus(HttpStatus.OK)
    public List<Reponse> listerQuestion() {
        try {
            return reponseService.List();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la récupération des reponse", e);
        }
    }

    @GetMapping("/listeReponse/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Reponse> getReponseParId(@PathVariable Long id) {
        try {
            return reponseService.findById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la récupération d'une reponse par ID", e);
        }
    }

    @PutMapping("/modifier/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Reponse modifier(@PathVariable Long id, @RequestBody Reponse reponse) {
        try {
            return reponseService.update(reponse, id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la modification d'une reponse", e);
        }
    }

    @DeleteMapping("/supprimer/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimer(@PathVariable Long id) {
        try {
            reponseService.delete(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la suppression d'une reponse", e);
        }
    }
}
