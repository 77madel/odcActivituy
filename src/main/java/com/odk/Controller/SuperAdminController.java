package com.odk.Controller;

import com.odk.Entity.SuperAdmin;
import com.odk.Service.Interface.Service.SuperAdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/superadmin")
@AllArgsConstructor
public class SuperAdminController {

    private SuperAdminService superAdminService;

    @PostMapping("/ajout")
    @ResponseStatus(HttpStatus.CREATED)
    public SuperAdmin ajouter(@RequestBody SuperAdmin superAdmin){
        return superAdminService.add(superAdmin);
    }

    @GetMapping("/listeSuperAdmin")
    @ResponseStatus(HttpStatus.OK)
    public List<SuperAdmin> ListerSuperAdmin(){
        return superAdminService.List();
    }

    @GetMapping("/listesuperadmin/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<SuperAdmin> getPersonnelParId(@PathVariable Long id){
        return superAdminService.findById(id);
    }

    @PutMapping("/modifier/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public SuperAdmin Modifier(@PathVariable Long id, @RequestBody SuperAdmin superAdmin ){
        return superAdminService.update(superAdmin,id);
    }

    @DeleteMapping("/supprimer/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  supprimer(@PathVariable Long id){
        superAdminService.delete(id);
    }
}
