package com.odk.Service.Interface.Service;

import com.odk.Entity.Critere;
import com.odk.Entity.Etape;
import com.odk.Entity.Participant;
import com.odk.Repository.*;
import com.odk.Service.Interface.CrudService;
import com.odk.dto.EtapeDTO;
import com.odk.dto.EtapeMapper;
import com.odk.dto.ParticipantDTO;
import com.odk.helper.ExcelHelper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class EtapeService implements CrudService<Etape, Long> {

    private EtapeRepository etapeRepository;
    private ActiviteRepository activiteRepository;
    private ParticipantRepository participantRepository;
    private ActiviteParticipantRepository activiteParticipantRepository;
    private CritereRepository critereRepository;


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
        etape.mettreAJourStatut(); // Mise à jour du statut avant sauvegarde
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
        return etapeRepository.findById(id).map(e -> {
            // Si le nom n'est pas nul, mettre à jour
            if (entity.getNom() != null) {
                e.setNom(entity.getNom());
            }

            // Si le statut n'est pas nul, mettre à jour
            if (entity.getStatut() != null) {
                e.setStatut(entity.getStatut());
            }

            // Si l'activité est définie, la mettre à jour (vérifier si elle existe)
            if (entity.getActivite() != null) {
                if (activiteRepository.existsById(entity.getActivite().getId())) {
                    e.setActivite(entity.getActivite());
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Activité non trouvée");
                }
            }

            // Si le critère est défini, le mettre à jour (vérifier s'il existe)
            if (entity.getCritere() != null && !entity.getCritere().isEmpty()) {
                for (Critere critere : entity.getCritere()) {
                    if (critereRepository.existsById(critere.getId())) {
                        e.getCritere().add(critere); // Ajoute le critère à l'entité cible
                    } else {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Critère non trouvé : ID " + critere.getId());
                    }
                }
            }


            // Mise à jour du statut dynamiquement
            e.mettreAJourStatut();

            // Sauvegarder l'entité mise à jour
            return etapeRepository.save(e);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "L'id n'est pas disponible"));
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
        List<Participant> participants = ExcelHelper.excelToTutorials(file, activiteRepository, activiteParticipantRepository, participantRepository);

        // Récupérer l'étape par ID
        Etape etape = etapeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Étape non trouvée avec l'ID : " + id));

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
