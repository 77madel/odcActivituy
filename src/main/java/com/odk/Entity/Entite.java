package com.odk.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private String logo;
    private String description;

    @OneToMany(mappedBy = "entite")
    @JsonIgnore
    private List<Activite> activite;

    @OneToOne
    @JoinColumn(name = "responsable_id", referencedColumnName = "id")
    private Utilisateur responsable;

    // Ajout d'un constructeur prenant un ID pour la désérialisation
    public Entite(Long id) {
        this.id = id;
    }
}
