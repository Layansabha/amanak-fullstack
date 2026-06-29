// OtpService.java
package com.example.amanakk_backend;

import org.springframework.stereotype.Service;

@Service
public class OtpService {

    public boolean verifyOtp(String phoneNumber, String otp) {
        // Implement OTP verification logic here
        return true; // Placeholder for demonstration purposes
    }

    public void sendOtp(String phoneNumber) {
        // Implement OTP sending logic here (e.g., using SMS API)
        System.out.println("Sending OTP to " + phoneNumber);
    }
}
