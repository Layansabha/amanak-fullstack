package com.example.amanakk_backend;

import com.example.amanakk_backend.UserAccounts;
import com.example.amanakk_backend.IdNumbersRequest;
import com.example.amanakk_backend.LoginRequest;
import com.example.amanakk_backend.UserAccountsRequest;
import com.example.amanakk_backend.LoginResponse;

import java.text.ParseException;
import java.util.Optional;

public interface UserService {
    Optional<UserAccounts> findUserByPhoneOrId(String identifier);
    void sendOtp(String phoneNumber);
    void registerNewUser(UserAccountsRequest request) throws Exception;
    boolean verifyIdNumber(IdNumbersRequest request) throws ParseException;
    LoginResponse authenticateUser(LoginRequest request);
    UserAccounts updateUser(Long userId, UserAccounts updatedUser);
}
