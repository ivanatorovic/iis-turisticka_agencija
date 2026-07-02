package com.example.turisticka_agencija.projection;

public interface AdditionalActivityAnalyticsSummaryProjection {
    Long getTotalRegistrations();
    Long getTotalParticipants();
    Double getTotalRevenue();
    Double getAverageOccupancy();
}
