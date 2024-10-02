package com.odk.Entity;

import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Activite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String titre;

    @Temporal(TemporalType.DATE)
    private Date dateDebut = new Date();

    @Temporal(TemporalType.DATE)
    private Date dateFin;

    private String lieu;
    private String description;
    private int objectifParticipation;

    @OneToMany(mappedBy = "activite")
    private List<Participant> participants = new ArrayList<>();

    @OneToMany(mappedBy = "activite")
    @JsonIgnore  // Correct import pour ignorer lors de la sérialisation
    private List<Etape> etapes = new ArrayList<>();  // Initialisation par défaut

    // Ajout d'un constructeur prenant un ID pour la désérialisation
    public Activite(Long id) {
        this.id = id;
    }
}
