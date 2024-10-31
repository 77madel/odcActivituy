package com.odk.execption;

public class UtilisateurNotFoundException extends RuntimeException {
    public UtilisateurNotFoundException(String message) {
        super(message);
    }
}
