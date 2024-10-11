package com.odk.Service.Interface.Service;

import com.odk.Entity.ExcelParticipant;
import com.odk.Entity.Participant;
import com.odk.Repository.ParticipantRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
@AllArgsConstructor
public class ImportService {
   /* private static final Logger logger = LoggerFactory.getLogger(ImportService.class);

    @Autowired
    private ParticipantRepository participantRepository;

    public void importerParticipants(MultipartFile file) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                // Ignorer la première ligne si elle contient les en-têtes
                if (row.getRowNum() == 0) continue;

                Participant participant = new Participant();
                participant.setNom(row.getCell(0).getStringCellValue());
                participant.setPrenom(row.getCell(1).getStringCellValue());
                participant.setEmail(row.getCell(2).getStringCellValue());

                // Validation
                if (participant.getNom() == null || participant.getNom().isEmpty()) {
                    throw new IllegalArgumentException("Le nom est requis");
                }
                if (participant.getEmail() == null || !participant.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                    throw new IllegalArgumentException("L'email est invalide");
                }

                participantRepository.save(participant);
                logger.info("Participant importé : {}", participant.getNom());
            }
        } catch (IOException e) {
            logger.error("Erreur de lecture du fichier : {}", e.getMessage());
            throw new RuntimeException("Erreur de lecture du fichier", e);
        }
    }*/


   /* public void save(MultipartFile file) {
        try {
            List<Participant> participants = ExcelParticipant.excelToParticipant(file.getInputStream());
            participantRepository.saveAll(participants);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }*/

    public List<String> lireParticipantsDepuisExcel(MultipartFile file) throws IOException {
        List<String> participants = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            Cell cell = row.getCell(0);
            if (cell != null) {
                participants.add(cell.getStringCellValue());
            }
        }

        workbook.close();
        return participants;
    }

}
