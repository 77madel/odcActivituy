package com.odk.Controller;

import com.odk.Entity.Entite;
import com.odk.Service.Interface.Service.EntiteOdcService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/entite")
@AllArgsConstructor
public class EntiteOdcController {
    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/images";
    private EntiteOdcService entiteOdcService;

    @PostMapping(value = "/ajouter", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Entite ajouter(
            @RequestPart("entiteOdc") Entite entiteOdc,
            @RequestPart("logo") MultipartFile file) throws IOException {


        Entite entite = new Entite();
        entite.setNom(entiteOdc.getNom());
        entite.setDescription(entiteOdc.getDescription());

        String imageUUID;
        if (!file.isEmpty()) {
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
            Files.write(fileNameAndPath, file.getBytes());
        } else {
            imageUUID = ""; // ou une valeur par défaut
        }

        entite.setLogo(imageUUID);
        return entiteOdcService.add(entite);
    }

    @GetMapping("/ListeEntite")
    @ResponseStatus(HttpStatus.OK)
    public List<Entite> ListerEntite(){
        return entiteOdcService.List();
    }

    @GetMapping("/ListeEntite/{id}")
    @ResponseStatus(HttpStatus.OK)
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
