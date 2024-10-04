package com.odk.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ActiviteParticipantKey implements Serializable {

    @Column(name = "activite_id")
    private Long activiteId;

    @Column(name = "participant_id")
    private Long participantId;
}
