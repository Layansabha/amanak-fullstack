package com.example.amanakk_backend;

public class OtpRequest {
    private String phoneNumber;
    private String otp;

    // No-argument constructor (necessary for serialization)
    public OtpRequest() {}

    // Parameterized constructor for easier object creation
    public OtpRequest(String phoneNumber, String otp) {
        this.phoneNumber = phoneNumber;
        this.otp = otp;
    }

    // Getter and Setter for phoneNumber
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Getter and Setter for otp
    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
