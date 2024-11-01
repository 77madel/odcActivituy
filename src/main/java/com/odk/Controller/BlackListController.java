package com.odk.Controller;

import com.odk.Entity.BlackList;
import com.odk.Entity.Critere;
import com.odk.Service.Interface.Service.BlackListService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @ResponseStatus(HttpStatus.OK)
    public List<BlackList> lister() {
        return blackListService.List();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlackList> getCritereParId(@PathVariable Long id) {
        return blackListService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BlackList> modifier(@PathVariable Long id, @RequestBody BlackList blackList) {
        BlackList updatedBlacklist = blackListService.update(blackList, id);
        return updatedBlacklist != null ? ResponseEntity.ok(updatedBlacklist) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void supprimer(@PathVariable Long id) {
        blackListService.delete(id);
    }
}
