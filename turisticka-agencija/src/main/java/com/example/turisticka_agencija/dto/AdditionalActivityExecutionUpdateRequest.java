package com.example.turisticka_agencija.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AdditionalActivityExecutionUpdateRequest {

    private LocalDate activityDate;
    private LocalTime startTime;

    private Integer durationMinutes;
    private Integer capacity;

    private Long guideId;

    private Double price;

    public LocalDate getActivityDate() {
        return activityDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Long getGuideId() {
        return guideId;
    }

    public Double getPrice() {
        return price;
    }
}