package com.example.turisticka_agencija.dto;

public class YearlySalesSummaryDto {

    private int year;
    private long totalReservations;
    private long confirmedReservations;
    private long cancelledReservations;
    private double totalRevenue;
    private double averageReservationValue;

    public YearlySalesSummaryDto() {
    }

    public YearlySalesSummaryDto(int year,
                                 long totalReservations,
                                 long confirmedReservations,
                                 long cancelledReservations,
                                 double totalRevenue,
                                 double averageReservationValue) {
        this.year = year;
        this.totalReservations = totalReservations;
        this.confirmedReservations = confirmedReservations;
        this.cancelledReservations = cancelledReservations;
        this.totalRevenue = totalRevenue;
        this.averageReservationValue = averageReservationValue;
    }

    public int getYear() {
        return year;
    }

    public long getTotalReservations() {
        return totalReservations;
    }

    public long getConfirmedReservations() {
        return confirmedReservations;
    }

    public long getCancelledReservations() {
        return cancelledReservations;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public double getAverageReservationValue() {
        return averageReservationValue;
    }
}