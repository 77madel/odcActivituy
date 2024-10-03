package com.odk.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "chapitre_id")
    private Chapitre chapitre;

//    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Question> questions;
}
