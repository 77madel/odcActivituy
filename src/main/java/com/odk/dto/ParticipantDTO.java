package com.odk.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.odk.Entity.Activite;
import com.odk.Entity.Participant;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ParticipantDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String phone;
    private String genre;
    private Activite activite; // Ajoutez le nom de l'activité
    private boolean checkedIn;
    private LocalDateTime checkInTime;

    public ParticipantDTO(Long id, String nom, String prenom, String email, String phone, String genre, boolean checkedIn, LocalDateTime checkInTime) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.phone = phone;
        this.genre = genre;
        this.activite = getActivite();
        this.checkedIn = checkedIn;
        this.checkInTime = checkInTime;
    }

    public ParticipantDTO(Participant participant) {
        this.id = participant.getId();
        this.nom = participant.getNom();
        this.prenom = participant.getPrenom();
        this.email = participant.getEmail();
        this.phone = participant.getPhone();
        this.genre = participant.getGenre();
        this.activite = participant.getActivite();
        this.checkedIn = participant.isCheckedIn();
        this.checkInTime = participant.getCheckInTime();

    }

    public ParticipantDTO(Long id, String nom) {
        this.id = id;
        this.nom = nom;
    }
}
