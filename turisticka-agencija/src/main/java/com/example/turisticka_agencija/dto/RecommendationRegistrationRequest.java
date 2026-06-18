package com.example.turisticka_agencija.dto;

public record RecommendationRegistrationRequest(
        Long registrationId,
        Long executionId,
        Integer numberOfPeople,
        String status,
        Integer reservedSpots,
        Integer capacity
) {
}
