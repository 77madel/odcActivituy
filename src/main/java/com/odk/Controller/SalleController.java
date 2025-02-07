package com.odk.Controller;

import com.odk.Entity.Critere;
import com.odk.Entity.Salle;
import com.odk.Service.Interface.Service.SalleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salle")
@AllArgsConstructor
public class SalleController {

    private SalleService salleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Salle ajouter(@RequestBody Salle salle) {

        return salleService.add(salle);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Salle> lister() {
        return salleService.List();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Salle> getSalleParId(@PathVariable Long id) {
        return salleService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Salle> modifier(@PathVariable Long id, @RequestBody Salle salle) {
        Salle updatedSalle = salleService.update(salle, id);
        return updatedSalle != null ? ResponseEntity.ok(updatedSalle) : ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void supprimer(@PathVariable Long id) {

        salleService.delete(id);
    }

}
