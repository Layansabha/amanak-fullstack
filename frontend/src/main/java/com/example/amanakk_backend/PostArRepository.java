package com.example.amanakk_backend;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostArRepository extends JpaRepository<PostAr, Long> {
    // Assuming PostAr entities are sorted by a 'createdAt' field in descending order
    List<PostAr> findTop2ByOrderByCreatedAtDesc();
    List<PostAr> findAllByOrderByCreatedAtDesc();
}
