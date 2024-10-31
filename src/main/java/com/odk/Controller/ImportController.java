/*
package com.odk.Controller;

import com.odk.Entity.ResponseMessage;
import com.odk.Service.Interface.Service.ImportService;
import com.odk.helper.ExcelHelper;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.apache.tomcat.util.http.fileupload.FileUploadBase.MULTIPART_FORM_DATA;

@RestController
@RequestMapping("/api/import")
@AllArgsConstructor
public class ImportController {

    private ImportService importService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) throws FileNotFoundException {
        String message = "";
        String path = "src/main/resources/participant.xlsx";
        FileInputStream fileInputStream = new FileInputStream(new File(path));

        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                importService.save(file);

               message = "Uploaded the file successfully: " ;
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
               message = "Could not upload the file: ";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> getFile() {
        String filename = "participant.xlsx";
        InputStreamResource file = new InputStreamResource(importService.load());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }

}
*/
