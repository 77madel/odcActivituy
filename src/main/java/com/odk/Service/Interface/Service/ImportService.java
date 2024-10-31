/*
package com.odk.Service.Interface.Service;

import com.odk.Entity.Participant;
import com.odk.Repository.ActiviteRepository;
import com.odk.Repository.EtapeRepository;
import com.odk.Repository.ParticipantRepository;
import com.odk.helper.ExcelHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
@Service
@AllArgsConstructor
public class ImportService {

    private ParticipantRepository participantRepository;
    private ActiviteRepository activiteRepository;
    priv

    public void save(MultipartFile file) throws IOException {
        List<Participant> participants = ExcelHelper.excelToTutorials(file, activiteRepository);
        participantRepository.saveAll(participants);
    }


    public ByteArrayInputStream load() {
        List<Participant> tutorials = participantRepository.findAll();

        ByteArrayInputStream in = ExcelHelper.tutorialsToExcel(tutorials);
        return in;
    }

}
*/
