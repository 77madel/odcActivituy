package com.odk.Entity;

import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

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
//    @OneToMany(mappedBy = "cours", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Chapitre> chapitres;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "activite_id")
    private Activite activite;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "chapitre_id")
    private Chapitre chapitre;


}
