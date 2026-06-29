package com.example.amanakk_backend;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SuccessStoryRepository extends CrudRepository<SuccessStory, Integer> {

    @Query(value = "SELECT * FROM success", nativeQuery = true)
    List<SuccessStory> findAllInEnglish();

    @Query(value = "SELECT * FROM success_ar", nativeQuery = true)
    List<SuccessStory> findAllInArabic();

    @Query(value = "SELECT * FROM success_ar WHERE id = :id", nativeQuery = true)
    Optional<SuccessStory> findByIdInArabic(@Param("id") Integer id);
}
