package com.odk.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Participant extends Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*@ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "activite_id")
    private Activite activite;*/

    @OneToMany(mappedBy = "participant")
    @JsonManagedReference
    private List<ActiviteParticipant> activites;



}
