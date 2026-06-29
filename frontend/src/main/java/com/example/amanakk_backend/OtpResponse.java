package com.example.amanakk_backend;

public class OtpResponse {
    private boolean isVerified;

    // Constructor
    public OtpResponse(boolean isVerified) {
        this.isVerified = isVerified;
    }

    // Getter for isVerified
    public boolean isVerified() {
        return isVerified;
    }

    // Setter for isVerified (optional, if needed)
    public void setVerified(boolean verified) {
        isVerified = verified;
    }
}
