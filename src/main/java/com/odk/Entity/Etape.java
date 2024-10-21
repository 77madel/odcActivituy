package com.odk.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.odk.Enum.Statut;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Etape {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;

    @OneToMany(mappedBy = "etapeDebut", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference("etapeDebutRef")
    private List<Participant> listeDebut = new ArrayList<>();

    @OneToMany(mappedBy = "etapeResultat", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("etapeResultatRef")
    private List<Participant> listeResultat = new ArrayList<>();

    private Statut statut;

    public void addParticipantsToListeDebut(List<Participant> participants) {
        for (Participant participant : participants) {
            participant.setEtapeDebut(this);  // Associe à la liste début
            this.listeDebut.add(participant);
        }
    }

    public void addParticipantsToListeResultat(List<Participant> participants) {
        for (Participant participant : participants) {
            participant.setEtapeResultat(this);  // Associe à la liste résultat
            this.listeResultat.add(participant);
        }
    }



    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "critere_id")
    private Critere critere;

    // Ajoutez un constructeur prenant un ID
    public Etape(Long id) {
        this.id = id;
    }



}
