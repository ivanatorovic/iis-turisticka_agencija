package com.example.turisticka_agencija.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AdditionalActivityExecutionResponse {

    private Long id;

    private Long arrangementTermId;
    private String arrangementName;
    private LocalDate arrangementStartDate;
    private LocalDate arrangementEndDate;

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
    private Long guideId;
    private String guideFirstName;
    private String guideLastName;
    private String guideUsername;
    private String status;

    public AdditionalActivityExecutionResponse(
            Long id,
            Long arrangementTermId,
            String arrangementName,
            LocalDate arrangementStartDate,
            LocalDate arrangementEndDate,
            Long guideId,
            String guideFirstName,
            String guideLastName,
            String guideUsername,
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
            double price,
            String status
    ) {
        this.id = id;
        this.arrangementTermId = arrangementTermId;
        this.arrangementName = arrangementName;
        this.arrangementStartDate = arrangementStartDate;
        this.arrangementEndDate = arrangementEndDate;
        this.guideId = guideId;
        this.guideFirstName = guideFirstName;
        this.guideLastName = guideLastName;
        this.guideUsername = guideUsername;
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
        this.status = status;
    }

    public Long getId() { return id; }
    public Long getArrangementTermId() { return arrangementTermId; }
    public String getArrangementName() { return arrangementName; }
    public LocalDate getArrangementStartDate() { return arrangementStartDate; }
    public LocalDate getArrangementEndDate() { return arrangementEndDate; }
    public Long getGuideId() { return guideId; }
    public String getGuideFirstName() { return guideFirstName; }
    public String getGuideLastName() { return guideLastName; }
    public String getGuideUsername() { return guideUsername; }
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
    public String getStatus() {
        return status;
    }
}