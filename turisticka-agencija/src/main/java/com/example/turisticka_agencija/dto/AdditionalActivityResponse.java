package com.example.turisticka_agencija.dto;

import java.util.List;

public class AdditionalActivityResponse {

    private Long id;
    private String name;
    private String description;
    private String location;
    private String imageUrl;
    private Long createdById;
    private String createdByUsername;
    private List<CategoryResponse> categories;

    public AdditionalActivityResponse(
            Long id,
            String name,
            String description,
            String location,
            String imageUrl,
            Long createdById,
            String createdByUsername,
            List<CategoryResponse> categories
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.imageUrl = imageUrl;
        this.createdById = createdById;
        this.createdByUsername = createdByUsername;
        this.categories = categories;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public String getCreatedByUsername() {
        return createdByUsername;
    }

    public List<CategoryResponse> getCategories() {
        return categories;
    }
}