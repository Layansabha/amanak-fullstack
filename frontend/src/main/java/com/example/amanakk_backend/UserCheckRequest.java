package com.example.amanakk_backend;

public class UserCheckRequest {
    private String identifier;

    public UserCheckRequest() { }

    public UserCheckRequest(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
