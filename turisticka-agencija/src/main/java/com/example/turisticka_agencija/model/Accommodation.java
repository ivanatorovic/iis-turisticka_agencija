package com.example.turisticka_agencija.model;

import jakarta.persistence.*;

@Entity
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private AccommodationCategory category;

    public Accommodation() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AccommodationCategory getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(AccommodationCategory category) {
        this.category = category;
    }
}