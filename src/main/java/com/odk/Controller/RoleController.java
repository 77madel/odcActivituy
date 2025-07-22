package com.odk.Controller;

import com.odk.Entity.Role;
import com.odk.Service.Interface.Service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/role")
@AllArgsConstructor
public class RoleController {

    private RoleService roleService;

    @PostMapping
    /*@PreAuthorize("hasRole('SUPERADMIN')")*/
    @ResponseStatus(HttpStatus.CREATED)
    public Role ajouter(@RequestBody Role role) {
        try {
            return roleService.add(role);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Erreur lors de l'ajout du role", e);
        }
    }

    @GetMapping
   /* @PreAuthorize("hasRole('SUPERADMIN')")*/
    @ResponseStatus(HttpStatus.OK)
    public List<Role> listerRole() {
        try {
            return roleService.List();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la récupération des roles", e);
        }
    }

    @GetMapping("/{id}")
   /* @PreAuthorize("hasRole('SUPERADMIN')")*/
    @ResponseStatus(HttpStatus.OK)
    public Optional<Role> getRoleParId(@PathVariable Long id) {
        try {
            return roleService.findById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la récupération du role par ID", e);
        }
    }

    @PatchMapping("/{id}")
    /*@PreAuthorize("hasRole('SUPERADMIN')")*/
    @ResponseStatus(HttpStatus.OK)
    public Role modifier(@PathVariable Long id, @RequestBody Role role) {
        try {
            return roleService.update(role, id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la modification du role", e);
        }
    }

    @DeleteMapping("/{id}")
    /*@PreAuthorize("hasRole('SUPERADMIN')")*/
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimer(@PathVariable Long id) {
        try {
            roleService.delete(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la suppression du role", e);
        }
    }

}
