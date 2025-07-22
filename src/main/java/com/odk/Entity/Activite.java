package com.odk.Entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.odk.Enum.Statut;
import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import java.util.*;

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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateDebut = new Date();

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateFin;

    private Statut statut;

    private String lieu;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer objectifParticipation;

    /*@ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "etape_id")
    @JsonIgnore
    private Etape etape;
*/
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "entite_id")
    @JsonIgnore
    private Entite entite;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "typeActivite_id")
    @JsonIgnore
    private TypeActivite typeActivite;

    @ManyToMany
    @JoinTable(
            name = "activite_etape",
            joinColumns = @JoinColumn(name = "activite_id"),
            inverseJoinColumns = @JoinColumn(name = "etape_id")
    )
    private List<Etape> etapes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "created_by")
    private Utilisateur createdBy;

    @ManyToOne
    @JoinColumn(name = "salleId")
    private Salle salleId;


    // Ajout d'un constructeur prenant un ID pour la désérialisation
    public Activite(Long id) {
        this.id = id;
    }

    public void mettreAJourStatut() {
        Date maintenant = new Date();
        if (dateDebut != null && dateFin != null) {
            if (maintenant.before(dateDebut)) {
                this.statut = Statut.En_Attente;
            } else if (maintenant.after(dateFin)) {
                this.statut = Statut.Termine;
            } else {
                this.statut = Statut.En_Cours;
            }
            System.out.println("Statut mis à jour : " + this.statut);
        } else {
            throw new RuntimeException("Les dates de début et de fin doivent être définies pour gérer le statut.");
        }
    }


}
