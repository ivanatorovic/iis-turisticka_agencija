package com.example.turisticka_agencija.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AdditionalActivityExecutionResponse {

    private Long id;

    private Long arrangementTermId;

    private Long additionalActivityId;
    private String activityName;
    private String activityDescription;
    private String activityLocation;
    private String imageUrl;

    private LocalDate activityDate;
    private LocalTime startTime;

    private int durationMinutes;
    private int capacity;
    private int reservedSpots;
    private int availableSpots;

    private double price;

    public AdditionalActivityExecutionResponse(
            Long id,
            Long arrangementTermId,
            Long additionalActivityId,
            String activityName,
            String activityDescription,
            String activityLocation,
            String imageUrl,
            LocalDate activityDate,
            LocalTime startTime,
            int durationMinutes,
            int capacity,
            int reservedSpots,
            int availableSpots,
            double price
    ) {
        this.id = id;
        this.arrangementTermId = arrangementTermId;
        this.additionalActivityId = additionalActivityId;
        this.activityName = activityName;
        this.activityDescription = activityDescription;
        this.activityLocation = activityLocation;
        this.imageUrl = imageUrl;
        this.activityDate = activityDate;
        this.startTime = startTime;
        this.durationMinutes = durationMinutes;
        this.capacity = capacity;
        this.reservedSpots = reservedSpots;
        this.availableSpots = availableSpots;
        this.price = price;
    }

    public Long getId() { return id; }
    public Long getArrangementTermId() { return arrangementTermId; }
    public Long getAdditionalActivityId() { return additionalActivityId; }
    public String getActivityName() { return activityName; }
    public String getActivityDescription() { return activityDescription; }
    public String getActivityLocation() { return activityLocation; }
    public String getImageUrl() { return imageUrl; }
    public LocalDate getActivityDate() { return activityDate; }
    public LocalTime getStartTime() { return startTime; }
    public int getDurationMinutes() { return durationMinutes; }
    public int getCapacity() { return capacity; }
    public int getReservedSpots() { return reservedSpots; }
    public int getAvailableSpots() { return availableSpots; }
    public double getPrice() { return price; }
}