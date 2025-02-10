package com.odk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.odk.Entity.Etape;
import com.odk.Entity.Liste;
import com.odk.Entity.Participant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
public class ListeDTO {
    private Long id;
    @JsonProperty("dateHeure")
    private LocalDateTime dateHeure;
    private boolean listeDebut;
    private boolean listeResultat;
    private Etape etape;

    // Constructeur pour mapper Liste -> ListeDTO
    public ListeDTO(Liste liste) {
        this.id = liste.getId();
        this.dateHeure = liste.getDateHeure();
        this.listeDebut = liste.isListeDebut();
        this.listeResultat = liste.isListeResultat();
        this.etape = liste.getEtape();
    }

}
