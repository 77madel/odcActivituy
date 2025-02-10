package com.odk.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;

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
    private Activite activite;

    @ManyToOne
    @MapsId("participantId")
    @JoinColumn(name = "participant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Participant participant;

    private LocalDate date;
}
