package com.example.turisticka_agencija.dto;

public class AdditionalActivityResponse {

    private Long id;
    private String name;
    private String description;
    private double price;
    private int durationMinutes;
    private String location;
    private String imageUrl;
    private Long createdById;
    private String createdByUsername;

    public AdditionalActivityResponse(
            Long id,
            String name,
            String description,
            double price,
            int durationMinutes,
            String location,
            String imageUrl,
            Long createdById,
            String createdByUsername
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.durationMinutes = durationMinutes;
        this.location = location;
        this.imageUrl = imageUrl;
        this.createdById = createdById;
        this.createdByUsername = createdByUsername;
    }

    public Long getId() { return id; }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public double getPrice() { return price; }

    public int getDurationMinutes() { return durationMinutes; }

    public String getLocation() { return location; }

    public String getImageUrl() { return imageUrl; }

    public Long getCreatedById() { return createdById; }

    public String getCreatedByUsername() { return createdByUsername; }
}