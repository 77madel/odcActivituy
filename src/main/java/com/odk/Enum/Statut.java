package com.odk.Enum;

public enum Statut {
    En_Cours("En_Cours"),
    En_Attente("En_Attente"),
    Termine("Termine")
    ;

    private final String value;

    Statut(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
