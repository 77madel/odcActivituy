package com.odk.Entity;

import com.odk.Enum.Genre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatistiqueGenre {

    private String mois;
    private Genre genre;
    private Long nombreParticipants;
}