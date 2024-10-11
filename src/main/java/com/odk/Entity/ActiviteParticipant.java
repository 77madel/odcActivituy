package com.odk.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.odk.Entity.Activite;
import com.odk.Entity.ActiviteParticipantKey;
import com.odk.Entity.Participant;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiviteParticipant {

    @EmbeddedId
    private ActiviteParticipantKey id;

    @ManyToOne
    @MapsId("activiteId")
    @JoinColumn(name = "activite_id")
    @JsonBackReference
    private Activite activite;

    @ManyToOne
    @MapsId("participantId")
    @JoinColumn(name = "participant_id")
    @JsonBackReference
    private Participant participant;

    private LocalDate dateFormation;
}
