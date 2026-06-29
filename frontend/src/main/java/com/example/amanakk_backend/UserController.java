package com.example.amanakk_backend;

import com.example.amanakk_backend.IdNumbersRequest;
import com.example.amanakk_backend.LoginRequest;
import com.example.amanakk_backend.UserAccountsRequest;
import com.example.amanakk_backend.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Endpoint for signing up a new user
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody UserAccountsRequest request) {
        try {
            userService.registerNewUser(request);
            return ResponseEntity.ok(new SignUpResponse(true, "User registered successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new SignUpResponse(false, "Registration failed: " + e.getMessage()));
        }
    }

    // Endpoint for verifying ID numbers
    @PostMapping("/verifyId")
    public ResponseEntity<SignUpResponse> verifyId(@RequestBody IdNumbersRequest request) {
        try {
            boolean isVerified = userService.verifyIdNumber(request);
            if (isVerified) {
                return ResponseEntity.ok(new SignUpResponse(true, "ID verification successful"));
            } else {
                return ResponseEntity.badRequest().body(new SignUpResponse(false, "ID verification failed"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new SignUpResponse(false, "Verification error: " + e.getMessage()));
        }
    }

    // Endpoint for user login
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            System.out.println("[LoginController] received login request for identifier=" + request.getIdOrPhone());
            LoginResponse loginResponse = userService.authenticateUser(request);
            if (loginResponse.isSuccess()) {
                return ResponseEntity.ok(loginResponse);
            } else {
                return ResponseEntity.badRequest().body(loginResponse);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new LoginResponse(false, "Login error: " + e.getMessage()));
        }
    }

    @PostMapping("/verifyUser")
    public ResponseEntity<UserCheckResponse> verifyUser(@RequestBody UserCheckRequest request) {
        Optional<UserAccounts> user = userService.findUserByPhoneOrId(request.getIdentifier());

        if (user.isPresent()) {
            String phoneNumber = user.get().getPhoneNumber();
            userService.sendOtp(phoneNumber); // Sends OTP to the user's phone number
            UserCheckResponse response = new UserCheckResponse(true, phoneNumber);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.ok(new UserCheckResponse(false, null));
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<LoginResponse> getProfile(@RequestParam String identifier) {
        Optional<UserAccounts> user = userService.findUserByPhoneOrId(identifier);

        if (user.isPresent()) {
            UserAccounts account = user.get();
            return ResponseEntity.ok(new LoginResponse(
                    true,
                    "Profile loaded",
                    account.getNationalId(),
                    account.getIdNumber(),
                    account.getFullName(),
                    account.getPhoneNumber(),
                    account.getEmail()
            ));
        }

        return ResponseEntity.badRequest().body(new LoginResponse(false, "User not found"));
    }


    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UserAccounts updatedUser) {
        try {
            UserAccounts user = userService.updateUser(userId, updatedUser);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating user: " + e.getMessage());
        }
    }}
