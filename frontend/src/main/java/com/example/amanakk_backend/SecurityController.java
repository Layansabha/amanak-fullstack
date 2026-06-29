package com.example.amanakk_backend;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/security")
public class SecurityController {

    // Toggle Biometric Login
    @PostMapping("/toggleBiometric")
    public ResponseEntity<?> toggleBiometricLogin(@RequestBody ToggleRequest request) {
        boolean isEnabled = request.isEnabled();
        // Logic to enable/disable biometric login
        // This could involve saving the state in the database
        return new ResponseEntity<>(new ApiResponse(isEnabled ? "Biometric login enabled" : "Biometric login disabled"), HttpStatus.OK);
    }

    // Toggle OTP Login
    @PostMapping("/toggleOtp")
    public ResponseEntity<?> toggleOtpLogin(@RequestBody ToggleRequest request) {
        boolean isEnabled = request.isEnabled();
        // Logic to enable/disable OTP login
        // This could involve saving the state in the database
        return new ResponseEntity<>(new ApiResponse(isEnabled ? "OTP login enabled" : "OTP login disabled"), HttpStatus.OK);
    }

    // Change Password
    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest request) {
        // Validate old password, change to new password
        // Return appropriate response
        boolean isChanged = changeUserPassword(request.getOldPassword(), request.getNewPassword());
        return new ResponseEntity<>(new ApiResponse(isChanged ? "Password changed successfully" : "Invalid old password"), HttpStatus.OK);
    }

    private boolean changeUserPassword(String oldPassword, String newPassword) {
        // Method to check old password and update with new password in the database
        // Assuming the old password is validated and the new one is hashed
        return true; // Return true if password change is successful
    }

    // Helper class to format API response
    private static class ApiResponse {
        private String message;

        public ApiResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    // Request class for toggle requests
    private static class ToggleRequest {
        private boolean enabled;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

    // Request class for changing password
    private static class PasswordChangeRequest {
        private String oldPassword;
        private String newPassword;

        public String getOldPassword() {
            return oldPassword;
        }

        public void setOldPassword(String oldPassword) {
            this.oldPassword = oldPassword;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
    }
}
