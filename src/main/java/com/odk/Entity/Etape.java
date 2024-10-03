package com.odk.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.odk.Enum.Statut;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Etape {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String critere;
    private String listeDebut;
    private String resultat;
    private Statut statut;



}
