package com.example.turisticka_agencija.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class PricingRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private PricingRuleType type;

    private double percentage;

    @ManyToOne
    private Arrangement arrangement;

    private LocalDate seasonStart;
    private LocalDate seasonEnd;

    private Integer minOccupancyPercent;

    private Integer maxDaysBeforeStart;
    private Integer minDaysBeforeStart;

    private boolean active = true;

    public PricingRule() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PricingRuleType getType() {
        return type;
    }

    public double getPercentage() {
        return percentage;
    }

    public Arrangement getArrangement() {
        return arrangement;
    }

    public LocalDate getSeasonStart() {
        return seasonStart;
    }

    public LocalDate getSeasonEnd() {
        return seasonEnd;
    }

    public Integer getMinOccupancyPercent() {
        return minOccupancyPercent;
    }

    public Integer getMaxDaysBeforeStart() {
        return maxDaysBeforeStart;
    }

    public Integer getMinDaysBeforeStart() {
        return minDaysBeforeStart;
    }

    public boolean isActive() {
        return active;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(PricingRuleType type) {
        this.type = type;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public void setArrangement(Arrangement arrangement) {
        this.arrangement = arrangement;
    }

    public void setSeasonStart(LocalDate seasonStart) {
        this.seasonStart = seasonStart;
    }

    public void setSeasonEnd(LocalDate seasonEnd) {
        this.seasonEnd = seasonEnd;
    }

    public void setMinOccupancyPercent(Integer minOccupancyPercent) {
        this.minOccupancyPercent = minOccupancyPercent;
    }

    public void setMaxDaysBeforeStart(Integer maxDaysBeforeStart) {
        this.maxDaysBeforeStart = maxDaysBeforeStart;
    }

    public void setMinDaysBeforeStart(Integer minDaysBeforeStart) {
        this.minDaysBeforeStart = minDaysBeforeStart;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}