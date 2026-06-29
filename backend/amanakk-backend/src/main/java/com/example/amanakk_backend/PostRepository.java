package com.example.amanakk_backend;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // Assuming Post entities are sorted by a 'createdAt' field in descending order
    List<Post> findTop2ByOrderByCreatedAtDesc();
    List<Post> findAllByOrderByCreatedAtDesc();
}
