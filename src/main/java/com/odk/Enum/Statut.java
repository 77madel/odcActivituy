package com.odk.Enum;

public enum Statut {
    EN_COURS("En_Cours"),
    EN_ATTENTE("En_Attente"),
    TERMINE("Termine")
    ;

    private final String value;

    Statut(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
