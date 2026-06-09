package com.example.turisticka_agencija.dto;

import java.util.List;

public class AdditionalActivityRequest {

    private String name;
    private String description;
    private String location;
    private String imageUrl;
    private List<Long> categoryIds;

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

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }
}