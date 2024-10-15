package com.odk.dto;



import com.odk.Entity.Etape;
import com.odk.Entity.Participant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;




@Mapper
public interface EtapeMapper {

    EtapeMapper INSTANCE = Mappers.getMapper(EtapeMapper.class);

    // Convert Mission entity to DTO
    @Mapping(target = "listeDebut", source = "listeDebut") // Mapper la liste mais ne pas mapper d'activités
    @Mapping(target = "listeResultat", source = "listeResultat")
    EtapeDTO ETAPE_DTO(Etape etape);

    default List<ParticipantDTO> mapParticipants(List<Participant> participants) {
        return participants.stream()
                .map(ParticipantMapper.INSTANCE::PARTICIPANT_DTO) // Utiliser le mapper Participant
                .toList();
    }


    List<EtapeDTO> listeEtape(List<Etape> etape);

    // Convert MissionDTO to entity
   // Etape etapeDTO(EtapeDTO etapeDTO);


}





