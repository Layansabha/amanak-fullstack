package com.example.amanakk_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserAccountsController {

    @Autowired
    private UserAccountsService userAccountsService;

    @PostMapping("/checkUser")
    public ResponseEntity<UserCheckResponse> checkUser(@RequestBody UserCheckRequest request) {
        Optional<UserAccounts> user = userAccountsService.findUserByPhoneOrId(request.getIdentifier());

        if (user.isPresent()) {
            userAccountsService.sendOtp(user.get().getPhoneNumber());  // Assuming OTP is sent via phone

            UserCheckResponse response = new UserCheckResponse(true, user.get().getPhoneNumber());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.ok(new UserCheckResponse(false, null));
        }
    }
}
