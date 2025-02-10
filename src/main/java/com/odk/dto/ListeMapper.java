package com.odk.dto;

import com.odk.Entity.Etape;
import com.odk.Entity.Liste;
import com.odk.Entity.Participant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface ListeMapper {

    ListeMapper INSTANCE = Mappers.getMapper(ListeMapper.class);

//     default ListeDTO toListeDTO(Liste liste) {
//        return new ListeDTO(
//                liste.getId(),
//                liste.getDateHeure(),
//                liste.isListeDebut(),
//                liste.isListeResultat(),
//                liste.getEtape()
//        );
//    }

    ListeDTO LISTE_DTO(Liste liste);

    List<ListeDTO> liste(List<Liste> liste);
}
