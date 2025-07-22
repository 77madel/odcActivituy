package com.odk.Controller;

import com.odk.Entity.Critere;
import com.odk.Entity.Etape;
import com.odk.Service.Interface.Service.CritereService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/critere")
@AllArgsConstructor
public class CritereController {

    private final CritereService critereService;

    @PostMapping
    @PreAuthorize("hasRole('PERSONNEL')")
    @ResponseStatus(HttpStatus.CREATED)
    public Critere ajouter(@RequestBody Critere critere) {
        return critereService.add(critere);
    }

    @GetMapping
    @PreAuthorize("hasRole('PERSONNEL') or hasRole('SUPERADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<Critere> lister() {
        return critereService.List();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PERSONNEL') or hasRole('SUPERADMIN')")
    public ResponseEntity<Critere> getCritereParId(@PathVariable Long id) {
        return critereService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('PERSONNEL')")
    public ResponseEntity<Critere> modifier(@PathVariable Long id, @RequestBody Critere critere) {
        Critere updatedCritere = critereService.update(critere, id);
        return updatedCritere != null ? ResponseEntity.ok(updatedCritere) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PERSONNEL')")
    @ResponseStatus(HttpStatus.OK)
    public void supprimer(@PathVariable Long id) {
        critereService.delete(id);
    }
}
