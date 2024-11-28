package com.odk.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.odk.Enum.Statut;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
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
    @ManyToOne
    @JoinColumn(name = "activite_id")
    @JsonBackReference
    private Activite activite;

    @OneToMany(mappedBy = "etapeDebut", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference("etapeDebutRef")
    private List<Participant> listeDebut = new ArrayList<>();

    @OneToMany(mappedBy = "etapeResultat", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("etapeResultatRef")
    private List<Participant> listeResultat = new ArrayList<>();
    private Date dateDebut;
    private Date dateFin;

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
