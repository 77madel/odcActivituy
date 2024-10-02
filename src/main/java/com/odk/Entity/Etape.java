package com.odk.Entity;

import com.odk.Enum.Statut;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Etape {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String critere;
    private String listeDebut;
    private String resultat;
    private Statut Statut;

    @ManyToOne
    @JoinColumn(name = "activite_id")
    private Activite activite;

}
