package com.odk.dto;

import com.odk.Entity.Liste;
import org.springframework.stereotype.Component;

@Component
public class ListeMapper {

    public ListeDTO toListeDTO(Liste liste) {
        return new ListeDTO(
                liste.getId(),
                liste.getDateHeure(),
                liste.isListeDebut(),
                liste.isListeResultat(),
                liste.getEtape()
        );
    }
}
