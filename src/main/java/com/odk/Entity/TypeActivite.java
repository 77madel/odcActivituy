package com.odk.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TypeActivite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;

    @ManyToMany(mappedBy = "typeActivites")
    @JsonIgnore // Ignorer la liste des entités lors de la sérialisation de TypeActivite

    private List<Entite> entites;

    public TypeActivite(Long id) {
        this.id = id;
    }
}
