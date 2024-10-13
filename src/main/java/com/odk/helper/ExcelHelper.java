package com.odk.helper;

import com.odk.Entity.Activite;
import com.odk.Entity.Participant;
import com.odk.Repository.ActiviteRepository;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.aspectj.weaver.ast.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


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

    public static List<Participant> excelToTutorials(MultipartFile is, ActiviteRepository activiteRepository) throws IOException {

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
            if (row.getCell(5) != null && row.getCell(5).getCellType() == CellType.STRING) {
                String nom = row.getCell(5).getStringCellValue();

                // Chercher l'activité par nom dans la base de données
                Optional<Activite> activiteOptional = activiteRepository.findByNom(nom);

                if (activiteOptional.isPresent()) {
                    participant.setActivite(activiteOptional.get());
                } else {
                    throw new RuntimeException("L'activité " + nom + " n'existe pas dans la base de données.");
                }
            } else {

                throw new RuntimeException("La cellule de l'activité est vide ou contient un type incorrect.");
            }

            participants.add(participant);
        }

        return participants;

//        try {
////            Workbook workbook = new XSSFWorkbook(is);
//
//            Sheet sheet = workbook.getSheetAt(0);
//            Iterator<Row> rows = sheet.iterator();
//
//            List<Participant> participants = new ArrayList<Participant>();
//
//            int rowNumber = 0;
//            while (rows.hasNext()) {
//                Row currentRow = rows.next();
//
//                // skip header
//                if (rowNumber == 0) {
//                    rowNumber++;
//                    continue;
//                }
//
//                Iterator<Cell> cellsInRow = currentRow.iterator();
//
//                Participant participant = new Participant();
//
//                int cellIdx = 0;
//                while (cellsInRow.hasNext()) {
//                    Cell currentCell = cellsInRow.next();
//
//                    switch (cellIdx) {
////                        case 0:
////                            participant.setId((long) currentCell.getNumericCellValue());
////                            break;
//
//                        case 0:
//                            participant.setNom(currentCell.getStringCellValue());
//                            break;
//
//                        case 1:
//                            participant.setPrenom(currentCell.getStringCellValue());
//                            break;
//
//                        case 2:
//                            participant.setEmail((currentCell.getStringCellValue()));
//                            break;
//                        case 3:
//                            participant.setPhone((currentCell.getStringCellValue()));
//                            break;
////                        case 5:
////                            participant.setGenre((currentCell.getStringCellValue()));
////                            break;
////                        case 6:
////                            participant.setActivite(String.valueOf(currentCell.getStringCellValue()));
////                            break;
//                        default:
//                            break;
//                    }
//
//                    cellIdx++;
//                }
//
//                participants.add(participant);
//            }
//
//            workbook.close();
//
//            return participants;
//        } catch (IOException e) {
//            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
//        }
    }

}
