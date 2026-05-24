package com.example.turisticka_agencija.dto;

public class AdditionalActivityShortResponse {

    private Long id;
    private String name;
    private String description;
    private String location;

    public AdditionalActivityShortResponse(Long id, String name, String description, String location) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }
}