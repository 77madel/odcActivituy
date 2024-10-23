package com.odk.Service.Interface.Service;

import com.odk.Entity.Etape;
import com.odk.Entity.Participant;
import com.odk.Repository.ActiviteRepository;
import com.odk.Repository.EtapeRepository;
import com.odk.Repository.ParticipantRepository;
import com.odk.Service.Interface.CrudService;
import com.odk.dto.EtapeDTO;
import com.odk.dto.EtapeMapper;
import com.odk.dto.ParticipantDTO;
import com.odk.helper.ExcelHelper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
public class EtapeService implements CrudService<Etape, Long> {

    private EtapeRepository etapeRepository;
    private ActiviteRepository activiteRepository;
    private ParticipantRepository participantRepository;

    private static final Logger logger = LoggerFactory.getLogger(EtapeService.class);

    // Convertit une entité Etape en DTO
    public EtapeDTO convertToDto(Etape etape) {
        EtapeDTO dto = new EtapeDTO();
        dto.setId(etape.getId());
        dto.setNom(etape.getNom());
        dto.setStatut(etape.getStatut());

        // Initialisation des listes si elles ne le sont pas déjà
        dto.setListeDebut(new ArrayList<>());
        dto.setListeResultat(new ArrayList<>());

        // Conversion des participants
        if (etape.getListeDebut() != null) {
            dto.setListeDebut(etape.getListeDebut().stream()
                    .map(ParticipantDTO::new)
                    .collect(Collectors.toList()));
        }

        if (etape.getListeResultat() != null) {
            dto.setListeResultat(etape.getListeResultat().stream()
                    .map(ParticipantDTO::new)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    @Override
    public Etape add(Etape etape) {
        return etapeRepository.save(etape);
    }

    @Override
    public List<Etape> List() {
        return etapeRepository.findAll();
    }


    public List<EtapeDTO> getAllEtapes() {
        List<Etape> etapes = etapeRepository.findAll();
        return etapes.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    @Override
    public Optional<Etape> findById(Long id) {

        return etapeRepository.findById(id);
    }
    @Override
    public Etape update(Etape entity, Long id) {
        Optional<Etape> etape = etapeRepository.findById(id);
        if (etape.isPresent()) {
          return   etapeRepository.save(entity);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<Etape> optionalEtape = etapeRepository.findById(id);
        optionalEtape.ifPresent(etape -> etapeRepository.delete(etape));
    }

    @Transactional
    public void addParticipantsToEtape(Long id, MultipartFile file, boolean toListeDebut) throws IOException {
        // Log de débogage
        System.out.println("toListeDebut : " + toListeDebut);

        // Convertir le fichier Excel en une liste de participants
        List<Participant> participants = ExcelHelper.excelToTutorials(file, activiteRepository);

        // Récupérer l'étape par ID
        Etape etape = etapeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Étape non trouvée avec l'ID : " + id));

        // Ajouter les participants à la bonne liste (liste début ou liste résultat)
        if (toListeDebut) {
            System.out.println("Ajout à la liste de début");
            etape.addParticipantsToListeDebut(participants);
        } else {
            System.out.println("Ajout à la liste de résultat");
            etape.addParticipantsToListeResultat(participants);
        }

        // Sauvegarder les participants et l'étape dans la base de données
        participantRepository.saveAll(participants);
        etapeRepository.save(etape);
    }



    public List<EtapeDTO> getEtapeDTO(Long id) {
        return EtapeMapper.INSTANCE.listeEtape(etapeRepository.findEtapeById(id));
    }



}
