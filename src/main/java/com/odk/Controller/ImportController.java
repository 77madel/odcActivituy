package com.odk.Controller;

import com.odk.Entity.ExcelParticipant;
import com.odk.Entity.ResponseMessage;
import com.odk.Service.Interface.Service.ImportService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/import")
@AllArgsConstructor
public class ImportController {

    private ImportService importService;

    /*@PostMapping("/participants")
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
    }*/

    /*@PostMapping("/participants")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Le fichier ne peut pas être vide."));
        }

        String message;
        if (ExcelParticipant.hasExcelFormat(file)) {
            try {
                importService.save(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Veuillez télécharger un fichier Excel!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }*/


}
