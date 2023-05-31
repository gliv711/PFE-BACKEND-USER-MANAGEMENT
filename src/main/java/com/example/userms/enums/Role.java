package com.example.userms.enums;

public enum Role {
    ADMIN("Administrateur"),
    USER_A("Entreprise"),

    USER_B ("Utilisateur");

    private String label;

    Role(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
