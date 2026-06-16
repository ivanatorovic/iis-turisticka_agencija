package com.example.turisticka_agencija.dto;

public record RecommendationActivityDto(
        Long activityId,
        String name,
        String description,
        String type,
        String location,
        String imageUrl
) {
}
