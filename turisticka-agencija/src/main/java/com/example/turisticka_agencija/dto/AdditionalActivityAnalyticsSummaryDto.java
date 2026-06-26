package com.example.turisticka_agencija.dto;

public class AdditionalActivityAnalyticsSummaryDto {
    private long totalRegistrations;
    private long totalParticipants;
    private double totalRevenue;
    private double averageOccupancy;

    public AdditionalActivityAnalyticsSummaryDto(
            long totalRegistrations,
            long totalParticipants,
            double totalRevenue,
            double averageOccupancy
    ) {
        this.totalRegistrations = totalRegistrations;
        this.totalParticipants = totalParticipants;
        this.totalRevenue = totalRevenue;
        this.averageOccupancy = averageOccupancy;
    }

    public long getTotalRegistrations() { return totalRegistrations; }
    public long getTotalParticipants() { return totalParticipants; }
    public double getTotalRevenue() { return totalRevenue; }
    public double getAverageOccupancy() { return averageOccupancy; }
}