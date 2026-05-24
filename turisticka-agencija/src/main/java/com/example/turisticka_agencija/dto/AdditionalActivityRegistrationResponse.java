package com.example.turisticka_agencija.dto;

import com.example.turisticka_agencija.model.AdditionalActivityRegistrationStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AdditionalActivityRegistrationResponse {

    private Long id;

    private Long executionId;

    private String activityName;
    private String activityDescription;
    private String activityLocation;
    private String imageUrl;

    private LocalDate activityDate;
    private LocalTime startTime;

    private int durationMinutes;
    private int numberOfParticipants;

    private double price;

    private LocalDateTime registrationDate;

    private AdditionalActivityRegistrationStatus status;

    public AdditionalActivityRegistrationResponse(
            Long id,
            Long executionId,
            String activityName,
            String activityDescription,
            String activityLocation,
            String imageUrl,
            LocalDate activityDate,
            LocalTime startTime,
            int durationMinutes,
            int numberOfParticipants,
            double price,
            LocalDateTime registrationDate,
            AdditionalActivityRegistrationStatus status
    ) {
        this.id = id;
        this.executionId = executionId;
        this.activityName = activityName;
        this.activityDescription = activityDescription;
        this.activityLocation = activityLocation;
        this.imageUrl = imageUrl;
        this.activityDate = activityDate;
        this.startTime = startTime;
        this.durationMinutes = durationMinutes;
        this.numberOfParticipants = numberOfParticipants;
        this.price = price;
        this.registrationDate = registrationDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getExecutionId() {
        return executionId;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public String getActivityLocation() {
        return activityLocation;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public LocalDate getActivityDate() {
        return activityDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public double getPrice() {
        return price;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public AdditionalActivityRegistrationStatus getStatus() {
        return status;
    }
}