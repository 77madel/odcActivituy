package com.odk.Controller;

import com.odk.Entity.*;
import com.odk.Service.Interface.Service.VigileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/vigile")
public class VigileController {

    private VigileService vigileService;

    @PostMapping("/ajout")
    @ResponseStatus(HttpStatus.CREATED)
    public Vigile ajouter(@RequestBody Vigile vigile){
       return vigileService.add(vigile);
    }

    @GetMapping("/liste")
    @ResponseStatus(HttpStatus.OK)
    public List<Vigile> Lister(){
        return vigileService.List();
    }

    @GetMapping("/liste/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Vigile> getPaParId(@PathVariable Long id){
        return vigileService.findById(id);
    }

    @PatchMapping("/modifier/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Vigile Modifier(@PathVariable Long id, @RequestBody Vigile vigile ){
        return vigileService.update(vigile,id);
    }

    @DeleteMapping("/supprimer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void  supprimer(@PathVariable Long id){
        vigileService.delete(id);
    }

}
