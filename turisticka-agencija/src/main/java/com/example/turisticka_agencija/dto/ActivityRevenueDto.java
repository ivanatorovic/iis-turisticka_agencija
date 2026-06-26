package com.example.turisticka_agencija.dto;

public class ActivityRevenueDto {
    private String activityName;
    private double revenue;

    public ActivityRevenueDto(String activityName, double revenue) {
        this.activityName = activityName;
        this.revenue = revenue;
    }

    public String getActivityName() { return activityName; }
    public double getRevenue() { return revenue; }
}