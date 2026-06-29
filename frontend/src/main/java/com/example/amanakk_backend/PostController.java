package com.example.amanakk_backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PostController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostService postService;

    @GetMapping("/posts")
    public List<PostResponse> getLatestPosts(
            @RequestParam String language,
            @RequestParam(value = "limit", defaultValue = "2") int limit
    ) {
        logger.info("Posts list requested: language={}, limit={}", language, limit);
        List<PostResponse> posts = postService.findLatestPosts(language, limit);
        logger.info("Posts list response: language={}, count={}", language, posts.size());
        return posts;
    }
}
