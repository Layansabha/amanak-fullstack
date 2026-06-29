package com.example.amanakk_backend;

public class UserCheckResponse {
    private boolean found;
    private String phoneNumber;

    public UserCheckResponse(boolean found, String phoneNumber) {
        this.found = found;
        this.phoneNumber = phoneNumber;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}