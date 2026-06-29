package com.example.turisticka_agencija.dto;

public interface YearlySalesSummaryProjection {
    Long getTotalReservations();
    Long getConfirmedReservations();
    Long getCancelledReservations();
    Double getTotalRevenue();
}