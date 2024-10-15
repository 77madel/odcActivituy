package com.odk.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EtapeDTO {

    private Long id;
    private String nom;
    private List<ParticipantDTO> listeDebut;
    private List<ParticipantDTO> listeResultat;

    public EtapeDTO() {
        this.id = getId();
        this.nom = getNom();
        this.listeDebut = new ArrayList<>();
        this.listeResultat = new ArrayList<>();
    }

}
