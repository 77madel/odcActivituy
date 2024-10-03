package com.odk.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contenu;

    private String type; // QCM ou question ouverte

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

}
