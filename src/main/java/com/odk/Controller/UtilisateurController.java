package com.odk.Controller;

import com.odk.Entity.Utilisateur;
import com.odk.Repository.UtilisateurRepository;
import com.odk.Service.Interface.Service.UtilisateurService;
import com.odk.dto.ChangePasswordDTO;
import com.odk.dto.UtilisateurDTO;
import com.odk.execption.IncorrectPasswordException;
import com.odk.execption.UtilisateurNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/utilisateur")
@AllArgsConstructor
public class UtilisateurController {

    private final UtilisateurRepository utilisateurRepository;
    private UtilisateurService utilisateurService;
    private static final Logger logger = LoggerFactory.getLogger(UtilisateurController.class);


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

    @GetMapping("/nombre") // Pas de paramètres
    public ResponseEntity<Long> getNombreUtilisateurs() {
        long count = utilisateurService.getNombreUtilisateurs();
        return ResponseEntity.ok(count); // Retourne le nombre d'utilisateurs
    }

   @PostMapping("/change-password")
   public void modifierMotDePasse(@RequestBody Map<String, String> activation) {
       this.utilisateurService.modifierMotDePasse(activation);
   }

}
