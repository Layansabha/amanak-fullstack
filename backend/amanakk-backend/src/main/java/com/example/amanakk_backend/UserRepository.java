package com.example.amanakk_backend;

import com.example.amanakk_backend.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
