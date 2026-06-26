package com.example.turisticka_agencija.dto;

public class ActivityOccupancyDto {
    private String activityName;
    private int capacity;
    private int reservedSpots;
    private double occupancyRate;

    public ActivityOccupancyDto(String activityName, int capacity, int reservedSpots, double occupancyRate) {
        this.activityName = activityName;
        this.capacity = capacity;
        this.reservedSpots = reservedSpots;
        this.occupancyRate = occupancyRate;
    }

    public String getActivityName() { return activityName; }
    public int getCapacity() { return capacity; }
    public int getReservedSpots() { return reservedSpots; }
    public double getOccupancyRate() { return occupancyRate; }
}