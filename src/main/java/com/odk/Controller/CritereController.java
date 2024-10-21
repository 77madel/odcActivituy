package com.odk.Controller;

import com.odk.Entity.Critere;
import com.odk.Entity.Etape;
import com.odk.Service.Interface.Service.CritereService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/critere")
@AllArgsConstructor
public class CritereController {

    private final CritereService critereService;

    @PostMapping("/ajout")
    @ResponseStatus(HttpStatus.CREATED)
    public Critere ajouter(@RequestBody Critere critere) {
        return critereService.add(critere);
    }

    @GetMapping("/liste")
    @ResponseStatus(HttpStatus.OK)
    public List<Critere> lister() {
        return critereService.List();
    }

    @GetMapping("/liste/{id}")
    public ResponseEntity<Critere> getCritereParId(@PathVariable Long id) {
        return critereService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/modifier/{id}")
    public ResponseEntity<Critere> modifier(@PathVariable Long id, @RequestBody Critere critere) {
        Critere updatedCritere = critereService.update(critere, id);
        return updatedCritere != null ? ResponseEntity.ok(updatedCritere) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/supprimer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void supprimer(@PathVariable Long id) {
        critereService.delete(id);
    }
}
