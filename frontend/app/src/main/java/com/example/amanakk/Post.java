package com.example.amanakk;

import com.google.gson.annotations.SerializedName;

public class Post {
    @SerializedName("id")
    private Long id;

    @SerializedName("title")
    private String title;

    @SerializedName("category")
    private String category;

    @SerializedName("subtitle")
    private String subtitle;

    @SerializedName("subcategory")
    private String subcategory;  // Added to handle the subcategory.

    @SerializedName("content")
    private String content;

    @SerializedName("image")
    private String imageBase64;  // Legacy field kept for compatibility with older responses.

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName(value = "created_at", alternate = {"createdAt"})
    private String createdAt;

    @SerializedName("date")
    private String date;

    @SerializedName("dtype")
    private String dtype;

    // Constructors
    public Post() {
    }

    public Post(Long id, String title, String subtitle, String category, String subcategory, String content, String imageBase64, String createdAt, String date, String dtype) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.category = category;
        this.subcategory = subcategory;
        this.content = content;
        this.imageBase64 = imageBase64;
        this.createdAt = createdAt;
        this.date = date;
        this.dtype = dtype;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }
}
