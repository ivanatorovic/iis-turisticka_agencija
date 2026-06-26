package com.example.turisticka_agencija.dto;

public class ActivityCancellationDto {
    private String activityName;
    private double cancelRate;

    public ActivityCancellationDto(String activityName, double cancelRate) {
        this.activityName = activityName;
        this.cancelRate = cancelRate;
    }

    public String getActivityName() { return activityName; }
    public double getCancelRate() { return cancelRate; }
}