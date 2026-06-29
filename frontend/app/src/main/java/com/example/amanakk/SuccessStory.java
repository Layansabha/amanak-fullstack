package com.example.amanakk;

import com.google.gson.annotations.SerializedName;

public class SuccessStory {

    @SerializedName("id")
    private long id;

    @SerializedName("title")
    private String title;

    @SerializedName("subtitle")
    private String subtitle;

    @SerializedName("category")
    private String category;

    @SerializedName("subcategory")
    private String subcategory;

    @SerializedName("content")
    private String content;

    @SerializedName("image")
    private String image;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName(value = "created_at", alternate = {"createdAt"})
    private String createdAt;

    // Default Constructor
    public SuccessStory() {}

    // Parameterized Constructor
    public SuccessStory(long id, String title, String subtitle, String category, String subcategory, String content, String image, String createdAt) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.category = category;
        this.subcategory = subcategory;
        this.content = content;
        this.image = image;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public long getId() { return id; }
    public String getTitle() { return title; }
    public String getSubtitle() { return subtitle; }
    public String getCategory() { return category; }
    public String getSubcategory() { return subcategory; }
    public String getContent() { return content; }
    public String getImage() { return image; }
    public String getImageUrl() { return imageUrl; }
    public String getCreatedAt() { return createdAt; }

    public void setId(long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setSubtitle(String subtitle) { this.subtitle = subtitle; }
    public void setCategory(String category) { this.category = category; }
    public void setSubcategory(String subcategory) { this.subcategory = subcategory; }
    public void setContent(String content) { this.content = content; }
    public void setImage(String image) { this.image = image; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
