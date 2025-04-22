package com.odk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntiteDTO {
    private Long id;
    private String nom;
    private String logo;
    private String description;
    private Long responsableId;              // uniquement l'ID du responsable
    private List<Long> typeActiviteIds;      // uniquement les IDs des types d'activités
}
