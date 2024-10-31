package com.odk.Entity;

import com.odk.Enum.Genre;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@Data
@NoArgsConstructor
public class StatistiqueGenre {
    // Getters et setters
    private Date date;
    private String genre;
    private Long count;

    // Constructeur
    public StatistiqueGenre( String genre, Long count) {
        this.genre = genre;
        this.count = count;
        this.date = date;

    }

}
