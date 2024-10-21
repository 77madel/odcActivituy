package com.odk.dto;

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
    private String lieu;
    private String description;
    private int objectifParticipation;
    private Statut etapeStatut;
    private List<ParticipantDTO> listeDebut; // Liste de participants pour l'étape de début
    private List<ParticipantDTO> listeResultat; // Liste de participants pour l'étape de résultat

    // Constructeur
    public ActiviteDTO(Long id, String nom, String titre, Date dateDebut, Date dateFin, String lieu, String description, int objectifParticipation, Statut etapeStatut, List<ParticipantDTO> listeDebut, List<ParticipantDTO> listeResultat) {
        this.id = id;
        this.nom = nom;
        this.titre = titre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.lieu = lieu;
        this.description = description;
        this.objectifParticipation = objectifParticipation;
        this.etapeStatut = Statut.valueOf(String.valueOf(etapeStatut));
        this.listeDebut = listeDebut;
        this.listeResultat = listeResultat;
    }
}
