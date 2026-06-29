package com.example.amanakk.network;

public class OtpRequest {
    private String phoneNumber;
    private String otp;

    public OtpRequest(String phoneNumber, String otp) {
        this.phoneNumber = phoneNumber;
        this.otp = otp;
    }

}
