package com.odk.helper;

import com.odk.Entity.Activite;
import com.odk.Entity.ActiviteParticipant;
import com.odk.Entity.ActiviteParticipantKey;
import com.odk.Entity.Participant;
import com.odk.Repository.ActiviteParticipantRepository;
import com.odk.Repository.ActiviteRepository;
import com.odk.Repository.ParticipantRepository;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
public class ExcelHelper {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = {"Nom", "Prenom", "Email", "Phone", "Genre", "Activite" };
    static String SHEET = "Participants";


    public static boolean hasExcelFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
      }

        return true;
    }

    public static ByteArrayInputStream tutorialsToExcel(List<Participant> participants) {

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);

            // Header
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }

            int rowIdx = 1;
            for (Participant participant : participants) {
                Row row = sheet.createRow(rowIdx++);

//                row.createCell(0).setCellValue(participant.getId());
                row.createCell(0).setCellValue(participant.getNom());
                row.createCell(1).setCellValue(participant.getPrenom());
                row.createCell(2).setCellValue(participant.getEmail());
                row.createCell(3).setCellValue(participant.getPhone());
                row.createCell(5).setCellValue(participant.getGenre());
                row.createCell(6).setCellValue(participant.getActivite() != null ? participant.getActivite().getNom() : "N/A");
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

    public static List<Participant> excelToTutorials(MultipartFile is, ActiviteRepository activiteRepository, ActiviteParticipantRepository activiteParticipantRepository, ParticipantRepository participantRepository) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook(is.getInputStream());
        XSSFSheet worksheet = (XSSFSheet) workbook.getSheetAt(0);
        List<Participant> participants = new ArrayList<>();

        for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
            Participant participant = new Participant();

            XSSFRow row = worksheet.getRow(i);

//            participant.setId((long) row.getCell(0).getNumericCellValue());
            participant.setNom(row.getCell(0).getStringCellValue());
            participant.setPrenom(row.getCell(1).getStringCellValue());
            participant.setEmail(row.getCell(2).getStringCellValue());
            // Vérifiez si la cellule du téléphone est de type numérique ou chaîne
            if (row.getCell(3).getCellType() == CellType.NUMERIC) {
                // Si c'est un nombre, on le convertit en String
                participant.setPhone(String.valueOf((int) row.getCell(3).getNumericCellValue()));
            } else if (row.getCell(3).getCellType() == CellType.STRING) {
                // Si c'est déjà une chaîne de caractères
                participant.setPhone(row.getCell(3).getStringCellValue());
            } else {
                throw new RuntimeException("Type de cellule inattendu pour le téléphone");
            }
            participant.setGenre(row.getCell(4).getStringCellValue());

            // Vérification si la cellule de l'activité est non nulle et de type chaîne
//            if (row.getCell(5) != null && row.getCell(5).getCellType() == CellType.STRING) {
//                String nomActivite = row.getCell(5).getStringCellValue();
            if (row.getCell(5) != null && row.getCell(5).getCellType() == CellType.STRING) {
                String nomActivite = row.getCell(5).getStringCellValue().trim(); // Suppression des espaces
//                Optional<Activite> activiteOptional = activiteRepository.findByNom(nomActivite);
                Optional<Activite> activiteOptional = activiteRepository.findByNomIgnoreCase(nomActivite); //Comparaison insensible a la case.

                if (activiteOptional.isPresent()) {
                    System.out.println(activiteOptional.get().getNom());
                    participant.setActivite(activiteOptional.get());

                    // Enregistrez le participant d'abord pour obtenir un ID valide
                    participantRepository.save(participant);

                    // Création de la clé composite pour l'entité ActiviteParticipant
                    ActiviteParticipant activiteParticipant = new ActiviteParticipant();
                    ActiviteParticipantKey key = new ActiviteParticipantKey();
                    key.setActiviteId(activiteOptional.get().getId());
                    key.setParticipantId(participant.getId()); // Participant ID devrait maintenant être défini

                    activiteParticipant.setId(key);
                    activiteParticipant.setActivite(activiteOptional.get());
                    activiteParticipant.setParticipant(participant);
                    activiteParticipant.setDate(LocalDate.now()); // Définissez la date

                    // Enregistrement de l'ActiviteParticipant
                    activiteParticipantRepository.save(activiteParticipant);
                } else {
                    throw new RuntimeException("L'activité " + nomActivite + " n'existe pas dans la base de données.");
                }
            } else {
                throw new RuntimeException("La cellule de l'activité est vide ou contient un type incorrect.");
            }



            participants.add(participant);
        }

        return participants;
    }

}
