package com.odk.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActiviteParticipant {

    @EmbeddedId
    private ActiviteParticipantKey id;

    @ManyToOne
    @MapsId("activiteId")
    @JoinColumn(name = "activite_id")
    private Activite activite;

    @ManyToOne
    @MapsId("participantId")
    @JoinColumn(name = "participant_id")
    private Participant participant;

    private LocalDate dateFormation;
}