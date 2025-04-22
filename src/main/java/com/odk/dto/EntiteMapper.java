package com.odk.dto;

import com.odk.Entity.Entite;
import com.odk.Entity.TypeActivite;
import com.odk.Entity.Utilisateur;
import com.odk.dto.EntiteDTO;

import java.util.List;
import java.util.stream.Collectors;

public class EntiteMapper {

    // Convertir une Entite vers un EntiteDTO
    public static EntiteDTO toDto(Entite entite) {
        if (entite == null) return null;

        List<Long> typeActiviteIds = null;
        if (entite.getTypeActivites() != null) {
            typeActiviteIds = entite.getTypeActivites()
                    .stream()
                    .map(TypeActivite::getId)
                    .collect(Collectors.toList());
        }

        Long responsableId = entite.getResponsable() != null ? entite.getResponsable().getId() : null;

        return new EntiteDTO(
                entite.getId(),
                entite.getNom(),
                entite.getLogo(),
                entite.getDescription(),
                responsableId,
                typeActiviteIds
        );
    }

    // Convertir un EntiteDTO vers une Entite (partiellement, les relations doivent être complétées ensuite)
    public static Entite toEntity(EntiteDTO dto) {
        if (dto == null) return null;

        Entite entite = new Entite();
        entite.setId(dto.getId());
        entite.setNom(dto.getNom());
        entite.setLogo(dto.getLogo());
        entite.setDescription(dto.getDescription());

        if (dto.getResponsableId() != null) {
            Utilisateur responsable = new Utilisateur();
            responsable.setId(dto.getResponsableId());
            entite.setResponsable(responsable);
        }

        if (dto.getTypeActiviteIds() != null) {
            List<TypeActivite> typeActivites = dto.getTypeActiviteIds().stream().map(id -> {
                TypeActivite ta = new TypeActivite();
                ta.setId(id);
                return ta;
            }).collect(Collectors.toList());
            entite.setTypeActivites(typeActivites);
        }

        return entite;
    }
}
