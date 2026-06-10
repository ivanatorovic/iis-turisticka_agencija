package com.turisticka_agencija.dodatne_aktivnosti.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDate;
import java.time.LocalTime;

@Node("AdditionalActivityExecution")
public class AdditionalActivityExecution {

    @Id
    private Long executionId;

    private LocalDate activityDate;
    private LocalTime startTime;

    private Integer durationMinutes;
    private Integer capacity;
    private Integer reservedSpots;
    private Double price;

    private String status;
    private Boolean prior;

    @JsonIgnore
    @Relationship(type = "EXECUTES_ACTIVITY")
    private AdditionalActivity activity;

    @JsonIgnore
    @Relationship(type = "PART_OF")
    private Arrangement arrangement;

    public AdditionalActivityExecution() {
    }

    public Long getExecutionId() {
        return executionId;
    }

    public void setExecutionId(Long executionId) {
        this.executionId = executionId;
    }

    public LocalDate getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(LocalDate activityDate) {
        this.activityDate = activityDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getReservedSpots() {
        return reservedSpots;
    }

    public void setReservedSpots(Integer reservedSpots) {
        this.reservedSpots = reservedSpots;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getPrior() {
        return prior;
    }

    public void setPrior(Boolean prior) {
        this.prior = prior;
    }

    public AdditionalActivity getActivity() {
        return activity;
    }

    public void setActivity(AdditionalActivity activity) {
        this.activity = activity;
    }

    public Arrangement getArrangement() {
        return arrangement;
    }

    public void setArrangement(Arrangement arrangement) {
        this.arrangement = arrangement;
    }
}