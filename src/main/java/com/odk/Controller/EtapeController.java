package com.odk.Controller;

import com.odk.Entity.Etape;
import com.odk.Service.Interface.Service.EtapeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/etape")
@AllArgsConstructor
public class EtapeController {

    private EtapeService etapeService;

    @PostMapping("/ajout")
    @ResponseStatus(HttpStatus.CREATED)
    public Etape ajouter(@RequestBody Etape etape){
        return etapeService.add(etape);
    }

    @GetMapping("/liste")
    @ResponseStatus(HttpStatus.OK)
    public List<Etape> ListerEtape(){
        return etapeService.List();
    }

    @GetMapping("/liste/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Etape> getEtapeParId(@PathVariable Long id){
        return etapeService.findById(id);
    }

    @PutMapping("/modifier/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Etape Modifier(@PathVariable Long id, @RequestBody Etape etape ){
        return etapeService.update(etape,id);
    }

    @DeleteMapping("/supprimer/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  supprimer(@PathVariable Long id){
        etapeService.delete(id);
    }


}
