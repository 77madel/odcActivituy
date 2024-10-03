package com.odk.Entity;

import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "etape_id")
    private Etape etape;
//
//    @OneToMany(mappedBy = "activite")
//    @JsonIgnore
//    private List<Participant> participants;

//    @OneToMany
//    @JsonIgnore
//    private List<Cours> cours = new ArrayList<>();
//
//    @OneToMany(mappedBy = "activite")
//    @JsonIgnore
//    private List<Etape> etapes;
    // Ajout d'un constructeur prenant un ID pour la désérialisation
    public Activite(Long id) {
        this.id = id;
    }
}
