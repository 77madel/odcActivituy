package com.odk.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Participant extends Utilisateur {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;

    @ManyToOne
    @JoinColumn(name = "activite_id")
    private Activite activite;
}
