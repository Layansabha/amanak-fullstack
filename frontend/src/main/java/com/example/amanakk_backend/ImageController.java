package com.example.amanakk_backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Optional;

@RestController
public class ImageController {
    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);


    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostArRepository postArRepository;

    @GetMapping({"/posts/{id}/image", "/api/v1/posts/{id}/image"})
    public ResponseEntity<byte[]> getPostImage(
            @PathVariable Long id,
            @RequestParam(value = "language", defaultValue = "en") String language
    ) {
        logger.info("Post image requested: postId={}, language={}", id, language);
        Optional<byte[]> image = findImage(id, language);
        if (!image.isPresent() || image.get() == null || image.get().length == 0) {
            logger.warn("Post image not found: postId={}, language={}", id, language);
            return ResponseEntity.notFound().build();
        }

        byte[] bytes = image.get();
        MediaType contentType = detectMediaType(bytes);
        logger.info("Post image response: postId={}, language={}, bytes={}, contentType={}",
                id, language, bytes.length, contentType);
        return ResponseEntity.ok()
                .contentType(contentType)
                .header(HttpHeaders.CACHE_CONTROL, "no-store, no-cache, must-revalidate, max-age=0")
                .header(HttpHeaders.PRAGMA, "no-cache")
                .header(HttpHeaders.EXPIRES, "0")
                .body(bytes);
    }

    private Optional<byte[]> findImage(Long id, String language) {
        if ("ar".equalsIgnoreCase(language)) {
            return postArRepository.findById(id).map(PostAr::getImage);
        }
        return postRepository.findById(id).map(Post::getImage);
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
