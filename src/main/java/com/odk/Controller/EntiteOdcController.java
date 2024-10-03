package com.odk.Controller;

import com.odk.Entity.Entite;
import com.odk.Service.Interface.Service.EntiteOdcService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/EntiteOdc")
@AllArgsConstructor
public class EntiteOdcController {
    private EntiteOdcService entiteOdcService;

    @PostMapping("ajout")
    @ResponseStatus(HttpStatus.CREATED)
    public Entite ajouter(@RequestBody Entite entiteOdc){
        return entiteOdcService.add(entiteOdc);
    }

    @GetMapping("/ListeEntite")
    @ResponseStatus(HttpStatus.FOUND)
    public List<Entite> ListerEntite(){
        return entiteOdcService.List();
    }

    @GetMapping("/ListeEntite/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Optional<Entite> getEnitteParId(@PathVariable Long id){
        return entiteOdcService.findById(id);
    }

    @PutMapping("/modifier/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Entite Modifier(@PathVariable Long id, @RequestBody Entite entiteOdc ){
        return entiteOdcService.update(entiteOdc,id);
    }

    @DeleteMapping("/supprimer/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  supprimer(@PathVariable Long id){
        entiteOdcService.delete(id);
    }


}
