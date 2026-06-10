package com.turisticka_agencija.dodatne_aktivnosti.dto;

public class AdditionalActivityDTO {

    private Long id;
    private String name;
    private String description;
    private String type;
    private String location;
    private String imageUrl;

    public AdditionalActivityDTO(
            Long id,
            String name,
            String description,
            String type,
            String location,
            String imageUrl
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.location = location;
        this.imageUrl = imageUrl;
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

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}