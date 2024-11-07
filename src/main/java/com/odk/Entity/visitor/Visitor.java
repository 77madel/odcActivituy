package com.odk.Entity.visitor;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "visitor")
@Data
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String utilisateur;
    private String ip;
    private String method;
    private String url;
    private String page;
    private String queryString;
    private String refererPage;
    private String userAgent;
    private LocalDateTime loggedTime;
    private boolean uniqueVisit;

}
