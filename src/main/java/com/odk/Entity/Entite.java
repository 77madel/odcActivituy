package com.odk.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "entite")
public class Entite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;

    // Ajout d'un constructeur prenant un ID pour la désérialisation
    public Entite(Long id) {
        this.id = id;
    }
}
