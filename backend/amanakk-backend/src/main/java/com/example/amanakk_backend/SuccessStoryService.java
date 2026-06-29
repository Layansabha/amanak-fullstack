package com.example.amanakk_backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SuccessStoryService {
    private static final Logger logger = LoggerFactory.getLogger(SuccessStoryService.class);

    @Autowired
    private SuccessStoryRepository successStoryRepository;

    public List<SuccessStoryResponse> getSuccessStories(String language) {
        List<SuccessStory> stories;
        if ("ar".equalsIgnoreCase(language)) {
            stories = successStoryRepository.findAllInArabic();
        } else {
            stories = successStoryRepository.findAllInEnglish();
        }
        return stories.stream()
                .map(story -> toResponse(story, language))
                .collect(Collectors.toList());
    }

    public Optional<byte[]> getImage(Integer id, String language) {
        Optional<SuccessStory> story = "ar".equalsIgnoreCase(language)
                ? successStoryRepository.findByIdInArabic(id)
                : successStoryRepository.findById(id);
        return story.map(SuccessStory::getImage)
                .filter(image -> image.length > 0);
    }

    private SuccessStoryResponse toResponse(SuccessStory story, String language) {
        SuccessStoryResponse response = new SuccessStoryResponse();
        response.setId(story.getId());
        response.setTitle(story.getTitle());
        response.setSubtitle(story.getSubtitle());
        response.setCategory(story.getCategory());
        response.setSubcategory(story.getSubcategory());
        response.setContent(story.getContent());
        response.setCreatedAt(story.getCreatedAt() == null
                ? null
                : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(story.getCreatedAt()));
        if (story.getImage() != null && story.getImage().length > 0) {
            response.setImageUrl(buildImageUrl(story.getId(), language));
        }
        return response;
    }

    private String buildImageUrl(Integer storyId, String language) {
        UriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/success-stories/")
                .path(storyId.toString())
                .path("/image");
        if ("ar".equalsIgnoreCase(language)) {
            builder.queryParam("language", "ar");
        }
        String imageUrl = builder.toUriString();
        logger.info("Generated Success Story imageUrl: storyId={}, language={}, imageUrl={}",
                storyId, language, imageUrl);
        return imageUrl;
    }
}
