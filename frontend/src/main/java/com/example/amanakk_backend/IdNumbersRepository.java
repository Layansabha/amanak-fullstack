package com.example.amanakk_backend;

import com.example.amanakk_backend.IdNumbers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface IdNumbersRepository extends JpaRepository<IdNumbers, Long> {

    // Custom method to find by ID number, National ID, and birthdate
    Optional<IdNumbers> findByIdNumberAndNationalIdAndBirthdate(String idNumber, String nationalId, Date birthdate);

    // Custom method to find by ID number and National ID only (used during registration)
    Optional<IdNumbers> findByIdNumberAndNationalId(String idNumber, String nationalId);
}
