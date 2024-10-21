package com.odk.dto;

public class RoleDTO {

    private String authority;

    public RoleDTO(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
