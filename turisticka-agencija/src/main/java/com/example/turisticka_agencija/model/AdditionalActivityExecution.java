package com.example.turisticka_agencija.model;

import jakarta.persistence.*;

@Entity
public class AdditionalActivityExecution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AdditionalActivity additionalActivity;

    @ManyToOne
    private ArrangementTerm arrangementTerm;

    @ManyToOne
    private ActivityTerm activityTerm;

    private int durationMinutes;

    private int capacity;

    private int reservedSpots;

    public AdditionalActivityExecution() {
    }

    public Long getId() {
        return id;
    }

    public AdditionalActivity getAdditionalActivity() {
        return additionalActivity;
    }

    public ArrangementTerm getArrangementTerm() {
        return arrangementTerm;
    }

    public ActivityTerm getActivityTerm() {
        return activityTerm;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getReservedSpots() {
        return reservedSpots;
    }

    public void setAdditionalActivity(AdditionalActivity additionalActivity) {
        this.additionalActivity = additionalActivity;
    }

    public void setArrangementTerm(ArrangementTerm arrangementTerm) {
        this.arrangementTerm = arrangementTerm;
    }

    public void setActivityTerm(ActivityTerm activityTerm) {
        this.activityTerm = activityTerm;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setReservedSpots(int reservedSpots) {
        this.reservedSpots = reservedSpots;
    }

    public int getAvailableSpots() {
        return capacity - reservedSpots;
    }
}