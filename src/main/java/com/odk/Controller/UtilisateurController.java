package com.odk.Controller;

import com.odk.Entity.Utilisateur;
import com.odk.Service.Interface.Service.UtilisateurService;
import com.odk.dto.UtilisateurDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/utilisateur")
@AllArgsConstructor
public class UtilisateurController {

    private UtilisateurService utilisateurService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Utilisateur ajouter(@RequestBody Utilisateur utilisateur){

        return utilisateurService.add(utilisateur);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UtilisateurDTO> Liste(){

        return utilisateurService.getAllUtilisateur();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Utilisateur> getPersonnelParId(@PathVariable Long id){
        return utilisateurService.findById(id);
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Utilisateur Modifier(@PathVariable Long id, @RequestBody Utilisateur utilisateur ){
        return utilisateurService.update(utilisateur,id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  supprimer(@PathVariable Long id){

        utilisateurService.delete(id);
    }

}
