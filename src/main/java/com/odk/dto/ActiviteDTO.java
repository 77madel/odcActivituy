package com.odk.dto;

import com.odk.Entity.Entite;
import com.odk.Entity.Etape;
import com.odk.Entity.TypeActivite;
import com.odk.Enum.Statut;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ActiviteDTO {
    private Long id;
    private String nom;
    private String titre;
    private Date dateDebut;
    private Date dateFin;
    private Statut statut;
    private String lieu;
    private String description;
    private int objectifParticipation;
    private Etape etape;
    private Entite entite;
    private TypeActivite typeActivite;
    private List<ParticipantDTO> listeDebut; // Liste de participants pour l'étape de début
    private List<ParticipantDTO> listeResultat; // Liste de participants pour l'étape de résultat

    // Constructeur
    public ActiviteDTO(Long id, String nom, String titre, Date dateDebut, Date dateFin, Statut statut, String lieu, String description, int objectifParticipation, Etape etape, Entite entite, TypeActivite typeActivite, List<ParticipantDTO> listeDebut, List<ParticipantDTO> listeResultat) {
        this.id = id;
        this.nom = nom;
        this.titre = titre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.statut = statut;
        this.lieu = lieu;
        this.description = description;
        this.objectifParticipation = objectifParticipation;
        this.entite = entite;
        this.etape = etape;
        this.typeActivite = typeActivite;
        this.listeDebut = listeDebut;
        this.listeResultat = listeResultat;
    }
}
