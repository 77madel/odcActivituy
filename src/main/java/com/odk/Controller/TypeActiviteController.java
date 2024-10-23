package com.odk.Controller;

import com.odk.Entity.TypeActivite;
import com.odk.Service.Interface.Service.TypeActiviteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/typeActivite")
@AllArgsConstructor
public class TypeActiviteController {

    private TypeActiviteService typeActiviteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TypeActivite> addTypeActivite(@RequestBody TypeActivite typeActivite) {
        TypeActivite saveTypeActivite = typeActiviteService.add(typeActivite);
        return ResponseEntity.ok(saveTypeActivite);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TypeActivite> getAllEtapes() {
        return typeActiviteService.List(); // Utilise le service pour récupérer les étapes sous forme de DTO
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TypeActivite> Modifier(@PathVariable Long id, @RequestBody TypeActivite typeActivite ){

        TypeActivite updateType =  typeActiviteService.update(typeActivite,id);
        return ResponseEntity.ok(updateType);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  supprimer(@PathVariable Long id){
        typeActiviteService.delete(id);
    }

}
