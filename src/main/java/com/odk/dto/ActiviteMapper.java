package com.odk.dto;

import com.odk.Entity.Activite;
import com.odk.Entity.Etape;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

public interface ActiviteMapper {

    ActiviteMapper INSTANCE = Mappers.getMapper(ActiviteMapper.class);

    // Convert Mission entity to DTO
    @Mapping(source = "listeDebut", target = "listeDebut", ignore = true)
    @Mapping(source = "listeResultat", target = "listeResultat")
    ActiviteDTO ACTIVITE_DTO(Activite activite);

    List<ActiviteDTO> listeActivite(List<Activite> activite);

    // Convert MissionDTO to entity
    // Etape etapeDTO(EtapeDTO etapeDTO);
}
