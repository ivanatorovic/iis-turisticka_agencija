package com.example.turisticka_agencija.dto;

public class AdditionalActivityRequest {

    private String name;
    private String description;
    private Double price;
    private Integer durationMinutes;
    private String location;
    private String imageUrl;

    public String getName() { return name; }

    public String getDescription() { return description; }

    public Double getPrice() { return price; }

    public Integer getDurationMinutes() { return durationMinutes; }

    public String getLocation() { return location; }

    public String getImageUrl() { return imageUrl; }

    public void setName(String name) { this.name = name; }

    public void setDescription(String description) { this.description = description; }

    public void setPrice(Double price) { this.price = price; }

    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }

    public void setLocation(String location) { this.location = location; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}