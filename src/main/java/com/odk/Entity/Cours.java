package com.odk.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;

    @ManyToMany
    @JoinTable( name = "cours_activite",
            joinColumns = @JoinColumn( name = "cours_id" ),
            inverseJoinColumns = @JoinColumn( name = "activite_id" ) )
    private List<Activite> activite = new ArrayList<>();

    @OneToMany(mappedBy = "cours", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chapitre> chapitres;


}
