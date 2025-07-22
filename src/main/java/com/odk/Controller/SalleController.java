package com.odk.Controller;

import com.odk.Entity.Critere;
import com.odk.Entity.Salle;
import com.odk.Service.Interface.Service.SalleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salle")
@AllArgsConstructor
public class SalleController {

    private SalleService salleService;

    @PostMapping
    @PreAuthorize("hasRole('PERSONNEL') or hasRole('SUPERADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Salle ajouter(@RequestBody Salle salle) {

        return salleService.add(salle);
    }

    @GetMapping
    @PreAuthorize("hasRole('PERSONNEL') or hasRole('SUPERADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<Salle> lister() {
        return salleService.List();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PERSONNEL') or hasRole('SUPERADMIN')")
    public ResponseEntity<Salle> getSalleParId(@PathVariable Long id) {
        return salleService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('PERSONNEL') or hasRole('SUPERADMIN')")
    public ResponseEntity<Salle> modifier(@PathVariable Long id, @RequestBody Salle salle) {
        Salle updatedSalle = salleService.update(salle, id);
        return updatedSalle != null ? ResponseEntity.ok(updatedSalle) : ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PERSONNEL') or hasRole('SUPERADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void supprimer(@PathVariable Long id) {

        salleService.delete(id);
    }

}
