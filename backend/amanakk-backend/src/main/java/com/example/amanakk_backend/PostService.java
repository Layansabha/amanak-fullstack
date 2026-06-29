package com.example.amanakk_backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostArRepository postArRepository;

    public List<PostResponse> findLatestPosts(String language, int limit) {
        int safeLimit = Math.max(1, Math.min(limit, 20));
        if ("ar".equals(language)) {
            List<PostAr> arabicPosts = postArRepository.findAllByOrderByCreatedAtDesc()
                    .stream()
                    .limit(safeLimit)
                    .collect(Collectors.toList());
            if (!arabicPosts.isEmpty()) {
                return arabicPosts.stream()
                        .map(this::toPostResponse)
                        .collect(Collectors.toList());
            }
        }
        return postRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .limit(safeLimit)
                .map(this::toPostResponse)
                .collect(Collectors.toList());
    }

    private PostResponse toPostResponse(Post post) {
        PostResponse response = new PostResponse();
        response.setId(post.getId());
        response.setTitle(post.getTitle());
        response.setSubtitle(post.getSubtitle());
        response.setCategory(post.getCategory());
        response.setSubcategory(post.getSubcategory());
        response.setContent(post.getContent());
        response.setCreatedAt(post.getCreatedAt());
        response.setDate(post.getDate());
        if (hasImage(post.getImage())) {
            response.setImageUrl(buildImageUrl(post.getId(), "en"));
        }
        return response;
    }

    private PostResponse toPostResponse(PostAr postAr) {
        PostResponse response = new PostResponse();
        response.setId(postAr.getId());
        response.setTitle(postAr.getTitle());
        response.setCategory(postAr.getCategory());
        response.setSubtitle(postAr.getSubtitle());
        response.setContent(postAr.getContent());
        response.setCreatedAt(postAr.getCreatedAt() != null ? postAr.getCreatedAt().toString() : null);
        if (hasImage(postAr.getImage())) {
            response.setImageUrl(buildImageUrl(postAr.getId(), "ar"));
        }
        return response;
    }

    private String buildImageUrl(Long postId, String language) {
        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/posts/")
                .path(postId.toString())
                .path("/image")
                .queryParam("language", language)
                .toUriString();
        logger.info("Generated imageUrl: postId={}, language={}, imageUrl={}", postId, language, imageUrl);
        return imageUrl;
    }

    private boolean hasImage(byte[] image) {
        return image != null && image.length > 0;
    }
}
