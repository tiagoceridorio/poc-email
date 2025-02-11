package com.example.email.auth;

public enum AuthType {
    APP_PASSWORD("Senha de Aplicativo"),
    OAUTH2("Login com Google");

    private final String displayName;

    AuthType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
