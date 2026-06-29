package com.example.turisticka_agencija.dto;

public interface MonthlyArrangementSalesProjection {
    Integer getMonth();
    Long getReservationCount();
    Double getRevenue();
}