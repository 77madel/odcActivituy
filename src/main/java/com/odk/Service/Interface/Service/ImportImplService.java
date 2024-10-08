package com.odk.Service.Interface.Service;

import com.odk.Entity.ExcelParticipant;
import com.odk.Entity.Participant;
import com.odk.Repository.ParticipantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class ImportImplService implements ImportService {

    private ParticipantRepository participantRepository;

    @Override
    public void save(MultipartFile file) {
        try {
            List<Participant> participantList = ExcelParticipant.excelToParticipant(file.getInputStream());
            participantRepository.saveAll(participantList);
        } catch (IOException ex) {
            throw new RuntimeException("Excel data is failed to store: " + ex.getMessage());
        }
    }
}
