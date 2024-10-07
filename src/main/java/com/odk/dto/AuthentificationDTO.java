package com.odk.dto;

import com.odk.Entity.Role;
import lombok.Data;


public record AuthentificationDTO(String username, String password) {

}
