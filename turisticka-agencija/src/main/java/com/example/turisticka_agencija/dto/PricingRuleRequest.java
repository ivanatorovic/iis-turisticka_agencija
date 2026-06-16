package com.example.turisticka_agencija.dto;

import com.example.turisticka_agencija.model.PricingRuleType;

import java.time.LocalDate;

public class PricingRuleRequest {

    private String name;
    private PricingRuleType type;
    private double percentage;

    private Long arrangementId;

    private LocalDate seasonStart;
    private LocalDate seasonEnd;

    private Integer minOccupancyPercent;

    private Integer maxDaysBeforeStart;
    private Integer minDaysBeforeStart;

    private boolean active = true;

    public String getName() {
        return name;
    }

    public PricingRuleType getType() {
        return type;
    }

    public double getPercentage() {
        return percentage;
    }

    public Long getArrangementId() {
        return arrangementId;
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
}