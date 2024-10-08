package com.odk.Controller;

import com.odk.Service.Interface.Service.ImportService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/import")
@AllArgsConstructor
public class ImportController {

    private ImportService importService;

    @PostMapping("/participants")
    public ResponseEntity<?> importerParticipants(@RequestParam("file") MultipartFile file) {
        // Vérification si le fichier est vide
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le fichier ne peut pas être vide.");
        }

        // Vérification si le fichier a la bonne extension
        if (!file.getOriginalFilename().endsWith(".xlsx")) {
            return ResponseEntity.badRequest().body("Le fichier doit être un fichier Excel (.xlsx).");
        }

        try {
            importService.importerParticipants(file);
            return ResponseEntity.ok("Importation réussie");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'importation : " + e.getMessage());
        }
    }

}
