package com.example.amanakk_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/otp")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/verify")
    public ResponseEntity<OtpResponse> verifyOtp(@RequestBody OtpRequest request) {
        // Verify the OTP for the provided phone number
        boolean isVerified = otpService.verifyOtp(request.getPhoneNumber(), request.getOtp());
        return ResponseEntity.ok(new OtpResponse(isVerified));
    }

    @PostMapping("/resend")
    public ResponseEntity<OtpResponse> resendOtp(@RequestParam String phoneNumber) {
        // Resend OTP to the provided phone number
        otpService.sendOtp(phoneNumber);
        return ResponseEntity.ok(new OtpResponse(true));  // Assuming resend is always successful here
    }
}
