package com.odk.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.odk.Entity.Role;
import com.odk.Entity.Utilisateur;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqRep {
    private String username;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String nom;
    private String prenom;
    private String email;
    private String phone;
    private String password;
    private Role role;
    private Utilisateur utilisateur;
    private List<Utilisateur> utilisateursList;
}
