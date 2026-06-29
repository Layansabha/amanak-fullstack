package com.example.amanakk_backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping({"/api/files", "/api/v1/files"})
public class FileUploadController {

    @Value("${file.storage.location}")
    private String storageLocation;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Cannot upload empty file");
        }

        try {
            Path rootLocation = Paths.get(storageLocation);
            Path targetLocation = rootLocation.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), targetLocation);

            String fileName = file.getOriginalFilename();
            return ResponseEntity.ok("File uploaded successfully: " + fileName);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Could not upload the file: " + file.getOriginalFilename() + " due to " + e.getMessage());
        }
    }
}
