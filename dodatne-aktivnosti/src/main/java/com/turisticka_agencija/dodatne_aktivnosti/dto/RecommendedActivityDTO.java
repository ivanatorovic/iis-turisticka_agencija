package com.turisticka_agencija.dodatne_aktivnosti.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class RecommendedActivityDTO {

    private Long executionId;
    private Long activityId;
    private String name;
    private String description;
    private String location;

    private LocalDate activityDate;
    private LocalTime startTime;
    private Integer durationMinutes;
    private Integer capacity;
    private Integer reservedSpots;
    private Double price;
    private String status;
    private Boolean prior;
    private Integer score;

    private ArrangementDTO arrangement;

    public RecommendedActivityDTO(Long executionId, Long activityId, String name, String description,
                                String location,
                                  LocalDate activityDate, LocalTime startTime,
                                  Integer durationMinutes, Integer capacity, Integer reservedSpots,
                                  Double price, String status, Boolean prior,
                                  ArrangementDTO arrangement,Integer score) {
        this.executionId = executionId;
        this.activityId = activityId;
        this.name = name;
        this.description = description;
        this.location = location;
        this.activityDate = activityDate;
        this.startTime = startTime;
        this.durationMinutes = durationMinutes;
        this.capacity = capacity;
        this.reservedSpots = reservedSpots;
        this.price = price;
        this.status = status;
        this.prior = prior;
        this.arrangement = arrangement;
        this.score = score;
    }

    public Long getExecutionId() { return executionId; }
    public Long getActivityId() { return activityId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }
    public LocalDate getActivityDate() { return activityDate; }
    public LocalTime getStartTime() { return startTime; }
    public Integer getDurationMinutes() { return durationMinutes; }
    public Integer getCapacity() { return capacity; }
    public Integer getReservedSpots() { return reservedSpots; }
    public Double getPrice() { return price; }
    public String getStatus() { return status; }
    public Boolean getPrior() { return prior; }
    public ArrangementDTO getArrangement() { return arrangement; }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}