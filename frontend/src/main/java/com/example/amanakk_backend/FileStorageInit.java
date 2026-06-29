package com.example.amanakk_backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileStorageInit implements CommandLineRunner {
    private final Path rootLocation = Paths.get("uploaded-files");

    @Override
    public void run(String... args) throws Exception {
        Files.createDirectories(rootLocation);
    }
}
