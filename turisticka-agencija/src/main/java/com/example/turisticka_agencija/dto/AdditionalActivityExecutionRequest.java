package com.example.turisticka_agencija.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AdditionalActivityExecutionRequest {

    private Long arrangementTermId;
    private Long additionalActivityId;

    private LocalDate activityDate;
    private LocalTime startTime;

    private Integer durationMinutes;
    private Integer capacity;

    private Double price;
    private Long guideId;

    public Long getArrangementTermId() { return arrangementTermId; }
    public Long getAdditionalActivityId() { return additionalActivityId; }
    public LocalDate getActivityDate() { return activityDate; }
    public LocalTime getStartTime() { return startTime; }
    public Integer getDurationMinutes() { return durationMinutes; }
    public Integer getCapacity() { return capacity; }
    public Double getPrice() { return price; }

    public void setArrangementTermId(Long arrangementTermId) { this.arrangementTermId = arrangementTermId; }
    public void setAdditionalActivityId(Long additionalActivityId) { this.additionalActivityId = additionalActivityId; }
    public void setActivityDate(LocalDate activityDate) { this.activityDate = activityDate; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public void setPrice(Double price) { this.price = price; }

    public Long getGuideId() {
        return guideId;
    }

    public void setGuideId(Long guideId) {
        this.guideId = guideId;
    }
}