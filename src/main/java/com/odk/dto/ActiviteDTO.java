package com.odk.dto;

import com.odk.Entity.Etape;
import jakarta.persistence.*;

import java.util.Date;

public class ActiviteDTO {
    private Long id;
    private String nom;
    private String titre;
    private Date dateDebut = new Date();
    private Date dateFin;
    private String lieu;
    private String description;
    private int objectifParticipation;
    private Etape etape;
}
