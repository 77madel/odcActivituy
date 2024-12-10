package com.odk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.odk.Enum.Statut;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EtapeDTO {

    private Long id;
    private String nom;
    private Statut statut;
    private List<Long> critere;
    private List<ParticipantDTO> listeDebut;
    private List<ParticipantDTO> listeResultat;

    public EtapeDTO() {
        this.listeDebut = new ArrayList<>();
        this.listeResultat = new ArrayList<>();
    }

}
