package com.example.amanakk_backend;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseRepository extends CrudRepository<Case, Long> {
    // Basic CRUD operations are automatically enabled
}
