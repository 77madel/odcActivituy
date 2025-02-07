package com.odk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.odk.Entity.Etape;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ListeDTO {
    private Long id;
    @JsonProperty("dateHeure")
    private LocalDateTime dateHeure;
    private boolean listeDebut;
    private boolean listeResultat;
    private Etape etape;
}
