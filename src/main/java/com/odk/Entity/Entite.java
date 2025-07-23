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

    @ManyToOne
    @JoinColumn(name = "responsable_id", referencedColumnName = "id")
    @JsonIgnore
    private Utilisateur responsable;

    @ManyToMany
    @JoinTable(
            name = "entite_type_activite",
            joinColumns = @JoinColumn(name = "entite_id"),
            inverseJoinColumns = @JoinColumn(name = "type_activite_id")
    )
    private List<TypeActivite> typeActivites;

    // Ajout d'un constructeur prenant un ID pour la désérialisation
    public Entite(Long id) {
        this.id = id;
    }
}
