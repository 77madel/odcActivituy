package com.odk.Controller;

import com.odk.Entity.Personnel;
import com.odk.Service.Interface.Service.PersonnelService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/personnel")
@AllArgsConstructor
public class PersonnelController {

    private PersonnelService personnelService;

    @PostMapping("/ajout")
    @ResponseStatus(HttpStatus.CREATED)
    public Personnel ajouter(@RequestBody Personnel personnel){
        return personnelService.add(personnel);
    }

    @GetMapping("/liste")
    @ResponseStatus(HttpStatus.OK)
    public List<Personnel> ListerPersonnel(){
        return personnelService.List();
    }

    @GetMapping("/liste/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Personnel> getPersonnelParId(@PathVariable Long id){
        return personnelService.findById(id);
    }

    @PatchMapping("/modifier/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Personnel Modifier(@PathVariable Long id, @RequestBody Personnel personnel ){
        return personnelService.update(personnel,id);
    }

    @DeleteMapping("/supprimer/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  supprimer(@PathVariable Long id){
        personnelService.delete(id);
    }

}
