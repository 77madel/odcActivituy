package com.odk.Controller;

import com.odk.Entity.Critere;
import com.odk.Entity.Etape;
import com.odk.Service.Interface.Service.CritereService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/critere")
@AllArgsConstructor
public class CritereController {

    private CritereService critereService;

    @PostMapping("/ajout")
    @ResponseStatus(HttpStatus.CREATED)
    public Critere ajouter(@RequestBody Critere critere){

        return critereService.add(critere);
    }

    @GetMapping("/liste")
    @ResponseStatus(HttpStatus.OK)
    public List<Critere> Lister(){

        return critereService.List();
    }

    @GetMapping("/liste/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Critere> getCritereParId(@PathVariable Long id){

        return critereService.findById(id);
    }

    @PatchMapping("/modifier/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Critere Modifier(@PathVariable Long id, @RequestBody Critere critere ){
        return critereService.update(critere,id);
    }

    @DeleteMapping("/supprimer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void  supprimer(@PathVariable Long id){
        critereService.delete(id);
    }

}
