package com.odk.Controller;

import com.odk.Entity.TypeActivite;
import com.odk.Service.Interface.Service.TypeActiviteService;
import com.odk.dto.TypeActiviteDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/typeActivite")
@AllArgsConstructor
public class TypeActiviteController {

    private TypeActiviteService typeActiviteService;

    @PostMapping
    @PreAuthorize("hasRole('PERSONNEL')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TypeActivite> addTypeActivite(@RequestBody TypeActivite typeActivite) {
        TypeActivite saveTypeActivite = typeActiviteService.add(typeActivite);
        return ResponseEntity.ok(saveTypeActivite);
    }

    @GetMapping
    @PreAuthorize("hasRole('PERSONNEL') or hasRole('SUPERADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<TypeActivite> getAllEtapes() {
        return typeActiviteService.List(); // Utilise le service pour récupérer les étapes sous forme de DTO
    }

    @GetMapping("/by-entite/{entiteId}")
    @PreAuthorize("hasRole('PERSONNEL') or hasRole('SUPERADMIN')")
    public List<TypeActiviteDTO> getByEntite(@PathVariable Long entiteId) {
        return typeActiviteService.getByEntiteId(entiteId);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('PERSONNEL')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TypeActivite> Modifier(@PathVariable Long id, @RequestBody TypeActivite typeActivite ){

        TypeActivite updateType =  typeActiviteService.update(typeActivite,id);
        return ResponseEntity.ok(updateType);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PERSONNEL')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  supprimer(@PathVariable Long id){
        typeActiviteService.delete(id);
    }

}
