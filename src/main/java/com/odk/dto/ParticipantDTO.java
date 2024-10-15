package com.odk.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.odk.Entity.Activite;
import com.odk.Entity.Etape;
import com.odk.Entity.Participant;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String phone;
    private String genre;
    private boolean checkedIn;
    private LocalDateTime checkInTime;  // Ajoutez ce champ si nécessaire
//    private Activite activite;
//    private Etape etapeDebut;
//    private Etape etapeResultat;

    public ParticipantDTO(Participant participant) {
        this.id = participant.getId();
        this.nom = participant.getNom();
        this.prenom = participant.getPrenom();
        this.email = participant.getEmail();
        this.phone = participant.getPhone();
        this.genre = participant.getGenre();
    }

    public ParticipantDTO(String s) {
    }

}
