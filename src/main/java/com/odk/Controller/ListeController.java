package com.odk.Controller;

import com.odk.Entity.Liste;
import com.odk.Service.Interface.Service.ListeService;
import com.odk.dto.ListeDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/liste")
public class ListeController {

    private ListeService listeService;

    @GetMapping
    public List<ListeDTO> getAllListes() {
        return listeService.getAllListes();
    }

    @GetMapping("/{id}")
    public Optional<Liste> getListeById(@PathVariable Long id) {
        return listeService.getFindById(id);
    }



}
