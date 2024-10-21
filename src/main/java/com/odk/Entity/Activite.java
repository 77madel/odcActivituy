package com.odk.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(columnDefinition = "TEXT")
    private String description;

    private int objectifParticipation;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "etape_id")
    @JsonIgnore
    private Etape etape;

    /*@ManyToMany
    @JoinTable(
            name = "ActiviteParticipant",
            joinColumns = @JoinColumn(name = "activite_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    private Set<Participant> participants;
*/
    // Ajout d'un constructeur prenant un ID pour la désérialisation
    public Activite(Long id) {
        this.id = id;
    }

   /* @OneToMany(mappedBy = "activite")
    @JsonManagedReference
    private List<ActiviteParticipant> participants;*/

}
