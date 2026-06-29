package com.example.amanakk_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAccountsService {

    @Autowired
    private UserAccountsRepository userAccountsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<UserAccounts> findUserByPhoneOrId(String identifier) {
        return userAccountsRepository.findByPhoneNumberOrNationalId(identifier, identifier);
    }

    public String authenticateUser(String identifier, String password) {
        Optional<UserAccounts> user = findUserByPhoneOrId(identifier);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return (user.get().getNationalId());  // Use national ID
        }
        return null;
    }

    public void sendOtp(String phoneNumber) {
        // Actual OTP sending logic should be implemented here
        System.out.println("OTP sent to: " + phoneNumber);  // Placeholder for actual OTP sending
    }
}
