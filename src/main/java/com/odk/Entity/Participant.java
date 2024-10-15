package com.odk.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Participant{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String phone;
    private String genre;
    private boolean checkedIn = false;  // Champ pour indiquer si le participant a été vérifié
    private LocalDateTime checkInTime;  // Champ pour l'heure de check-in


    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "activite_id")
    private Activite activite;

    // Participant dans la liste début d'une étape
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "etape_debut_id")
    @JsonBackReference
    private Etape etapeDebut;

    // Participant dans la liste résultat d'une étape
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "etape_resultat_id")
    @JsonBackReference
    private Etape etapeResultat;



}
