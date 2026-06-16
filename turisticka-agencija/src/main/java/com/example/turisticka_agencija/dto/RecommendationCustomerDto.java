package com.example.turisticka_agencija.dto;

public record RecommendationCustomerDto(
        Long customerId,
        String firstName,
        String lastName,
        String username,
        String email,
        String contact
) {
}
