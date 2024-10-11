package com.odk.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @ElementCollection // Pour stocker des listes simples
    private List<String> listeDebut = new ArrayList<>();

    @ElementCollection
    private List<String> listeResultat = new ArrayList<>();
    private Statut statut;

   @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "critere_id")
    private Critere critere;

    // Ajoutez un constructeur prenant un ID
    public Etape(Long id) {
        this.id = id;
    }

}
