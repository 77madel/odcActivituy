package com.odk.dto;

import com.odk.Entity.Etape;
import com.odk.Entity.Participant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper
public interface ParticipantMapper {

    ParticipantMapper INSTANCE = Mappers.getMapper(ParticipantMapper.class);

    // Convert Mission entity to DTO
//    @Mapping(source = "etapeDebut", target = "etapeDebut", ignore = true)
      @Mapping(target = "activite")
//    @Mapping(source = "etapeResultat", target = "etapeResultat")
    ParticipantDTO PARTICIPANT_DTO(Participant participant);

    List<ParticipantDTO> ListParticpant(List<Participant> participants);

    // Convert MissionDTO to entity
    // Etape etapeDTO(EtapeDTO etapeDTO);
}
