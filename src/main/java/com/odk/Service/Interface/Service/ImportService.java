package com.odk.Service.Interface.Service;

import com.odk.Entity.Participant;
import com.odk.Repository.ParticipantRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImportService {

    @Autowired
    private ParticipantRepository participantRepository;

    public void importerParticipants(MultipartFile file) throws IOException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            // Supposons que la première ligne contient les en-têtes
            if (row.getRowNum() == 0) continue;

            Participant participant = new Participant();
            participant.setNom(row.getCell(0).getStringCellValue());
            participant.setPrenom(row.getCell(1).getStringCellValue());
            participant.setEmail(row.getCell(2).getStringCellValue());
            // Autres champs
            participantRepository.save(participant);
        }
        workbook.close();
    }
}
