package com.odk.Controller;


import com.odk.Entity.Cours;
import com.odk.Entity.Role;
import com.odk.Service.Interface.Service.CoursService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cours")
@AllArgsConstructor
public class CoursController {

    private CoursService coursService;

    @PostMapping("/ajout")
    @ResponseStatus(HttpStatus.CREATED)
    public Cours ajouter(@RequestBody Cours cours) {
        try {
            return coursService.add(cours);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de l'ajout du cours", e);
        }
    }

    @GetMapping("/listeCours")
    @ResponseStatus(HttpStatus.OK)
    public List<Cours> listerCours() {
        try {
            return coursService.List();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la récupération des cours", e);
        }
    }

    @GetMapping("/listeCours/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Cours> getRoleParId(@PathVariable Long id) {
        try {
            return coursService.findById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la récupération du cours par ID", e);
        }
    }

    @PutMapping("/modifier/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cours modifier(@PathVariable Long id, @RequestBody Cours cours) {
        try {
            return coursService.update(cours, id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la modification du cours", e);
        }
    }

    @DeleteMapping("/supprimer/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimer(@PathVariable Long id) {
        try {
            coursService.delete(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la suppression du cours", e);
        }
    }
}
