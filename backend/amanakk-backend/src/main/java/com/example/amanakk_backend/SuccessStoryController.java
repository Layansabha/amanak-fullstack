package com.example.amanakk_backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class SuccessStoryController {
    private static final Logger logger = LoggerFactory.getLogger(SuccessStoryController.class);

    @Autowired
    private SuccessStoryService successStoryService;

    @GetMapping("/success-stories")
    public ResponseEntity<?> getSuccessStories(@RequestParam(value = "language", required = false) String language) {
        logger.info("Success Stories list requested: language={}", language);
        if (language == null || (!language.equalsIgnoreCase("en") && !language.equalsIgnoreCase("ar"))) {
            return ResponseEntity.badRequest().body("Invalid or missing 'language' parameter");
        }

        List<SuccessStoryResponse> stories = successStoryService.getSuccessStories(language);
        logger.info("Success Stories list response: language={}, count={}", language, stories.size());
        return ResponseEntity.ok(stories);
    }

    @GetMapping("/success-stories/{id}/image")
    public ResponseEntity<byte[]> getSuccessStoryImage(
            @PathVariable Integer id,
            @RequestParam(value = "language", defaultValue = "en") String language
    ) {
        logger.info("Success Story image requested: storyId={}, language={}", id, language);
        Optional<byte[]> image = successStoryService.getImage(id, language);
        if (!image.isPresent()) {
            logger.warn("Success Story image not found: storyId={}, language={}", id, language);
            return ResponseEntity.notFound().build();
        }

        byte[] bytes = image.get();
        MediaType contentType = detectMediaType(bytes);
        logger.info("Success Story image response: storyId={}, language={}, bytes={}, contentType={}",
                id, language, bytes.length, contentType);
        return ResponseEntity.ok()
                .contentType(contentType)
                .header(HttpHeaders.CACHE_CONTROL, "no-store, no-cache, must-revalidate, max-age=0")
                .header(HttpHeaders.PRAGMA, "no-cache")
                .header(HttpHeaders.EXPIRES, "0")
                .body(bytes);
    }

    private MediaType detectMediaType(byte[] image) {
        try {
            String contentType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(image));
            return contentType != null
                    ? MediaType.parseMediaType(contentType)
                    : MediaType.APPLICATION_OCTET_STREAM;
        } catch (IOException | IllegalArgumentException ignored) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
