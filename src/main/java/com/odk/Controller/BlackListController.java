package com.odk.Controller;

import com.odk.Entity.BlackList;
import com.odk.Entity.Critere;
import com.odk.Service.Interface.Service.BlackListService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blacklist")
@AllArgsConstructor
public class BlackListController {

    private BlackListService blackListService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BlackList ajouter(@RequestBody BlackList blackList) {
        return blackListService.add(blackList);
    }

    @GetMapping
    @PreAuthorize("hasRole('PERSONNEL') or hasRole('SUPERADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<BlackList> lister() {
        return blackListService.List();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PERSONNEL') or hasRole('SUPERADMIN')")
    public ResponseEntity<BlackList> getCritereParId(@PathVariable Long id) {
        return blackListService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('PERSONNEL') or hasRole('SUPERADMIN')")
    public ResponseEntity<BlackList> modifier(@PathVariable Long id, @RequestBody BlackList blackList) {
        BlackList updatedBlacklist = blackListService.update(blackList, id);
        return updatedBlacklist != null ? ResponseEntity.ok(updatedBlacklist) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PERSONNEL') or hasRole('SUPERADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void supprimer(@PathVariable Long id) {
        blackListService.delete(id);
    }
}
