package com.example.turisticka_agencija.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class DestinationCalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private SeasonType seasonType;

    @Enumerated(EnumType.STRING)
    private CalendarStatus status;

    @ManyToOne
    private Destination destination;

    public DestinationCalendar() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public SeasonType getSeasonType() {
        return seasonType;
    }

    public CalendarStatus getStatus() {
        return status;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setSeasonType(SeasonType seasonType) {
        this.seasonType = seasonType;
    }

    public void setStatus(CalendarStatus status) {
        this.status = status;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }
}