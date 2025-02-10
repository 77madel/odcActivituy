package com.odk.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Liste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateHeure;
    private boolean listeDebut;
    private boolean listeResultat;


//    @OneToMany(mappedBy = "liste", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference("listeRef") // Pour la sérialisation
//    private Set<Participant> participants = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "etape_id")
    @JsonBackReference("etape-liste")  // Doit correspondre à `@JsonManagedReference`
    private Etape etape;

    // Ajoutez un constructeur prenant un ID
    public Liste(Long id) {
        this.id = id;
    }



}
