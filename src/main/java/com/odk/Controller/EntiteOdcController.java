package com.odk.Controller;

import com.odk.Entity.EntiteOdc;
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
    public EntiteOdc ajouter(@RequestBody EntiteOdc entiteOdc){
        return entiteOdcService.add(entiteOdc);
    }

    @GetMapping("/ListeEntite")
    @ResponseStatus(HttpStatus.FOUND)
    public List<EntiteOdc> ListerEntite(){
        return entiteOdcService.List();
    }

    @GetMapping("/ListeEntite/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Optional<EntiteOdc> getEnitteParId(@PathVariable Long id){
        return entiteOdcService.findById(id);
    }

    @PutMapping("/modifier/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public EntiteOdc Modifier(@PathVariable Long id, @RequestBody EntiteOdc entiteOdc ){
        return entiteOdcService.update(entiteOdc,id);
    }

    @DeleteMapping("/supprimer/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  supprimer(@PathVariable Long id){
        entiteOdcService.delete(id);
    }


}
