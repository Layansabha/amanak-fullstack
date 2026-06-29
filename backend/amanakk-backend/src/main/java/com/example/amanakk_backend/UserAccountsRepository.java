package com.example.amanakk_backend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountsRepository extends JpaRepository<UserAccounts, Long> {
    Optional<UserAccounts> findByPhoneNumberOrNationalId(String phoneNum, String nationalId);
}
