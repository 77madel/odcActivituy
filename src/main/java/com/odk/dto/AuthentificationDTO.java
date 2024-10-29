package com.odk.dto;

import com.odk.Entity.Role;
import lombok.Data;

@Data
public class AuthentificationDTO {

    private String email;
    private String username;
    private String password;
    private Role role;
    private String token;
    private String nom;
    private String prenom;


}
