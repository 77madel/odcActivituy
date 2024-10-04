package com.odk.Controller;

import com.odk.Service.Interface.Service.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/import")
public class ImportController {

    @Autowired
    private ImportService importService;

    @PostMapping("/participants")
    public ResponseEntity<?> importerParticipants(@RequestParam("file") MultipartFile file) {
        try {
            importService.importerParticipants(file);
            return ResponseEntity.ok("Importation réussie");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'importation");
        }
    }
}
