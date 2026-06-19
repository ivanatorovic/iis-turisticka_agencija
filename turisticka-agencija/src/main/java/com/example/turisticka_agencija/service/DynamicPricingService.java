package com.example.turisticka_agencija.service;

import com.example.turisticka_agencija.model.*;
import com.example.turisticka_agencija.repository.PricingRuleRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class DynamicPricingService {

    private final PricingRuleRepository pricingRuleRepository;

    public DynamicPricingService(PricingRuleRepository pricingRuleRepository) {
        this.pricingRuleRepository = pricingRuleRepository;
    }

    public double calculatePrice(Arrangement arrangement, ArrangementTerm arrangementTerm) {
        double price = arrangement.getBasePrice();

        List<PricingRule> rules = pricingRuleRepository.findByActiveTrue();

        for (PricingRule rule : rules) {
            if (appliesToArrangement(rule, arrangement)
                    && shouldApplyRule(rule, arrangementTerm)) {

                price = price + (price * rule.getPercentage() / 100);
            }
        }

        return price;
    }

    public List<String> getPriceLabels(Arrangement arrangement, ArrangementTerm arrangementTerm) {
        List<String> labels = new ArrayList<>();

        List<PricingRule> rules = pricingRuleRepository.findByActiveTrue();

        for (PricingRule rule : rules) {
            if (appliesToArrangement(rule, arrangement)
                    && shouldApplyRule(rule, arrangementTerm)) {

                if (rule.getType() == PricingRuleType.SEASON) {
                    labels.add("Sezona");
                }

                if (rule.getType() == PricingRuleType.OCCUPANCY) {
                    labels.add("Velika popunjenost");
                }

                if (rule.getType() == PricingRuleType.LAST_MINUTE) {
                    labels.add("Last minute");
                }

                if (rule.getType() == PricingRuleType.EARLY_BOOKING) {
                    labels.add("Early booking");
                }
            }
        }

        int availableSpots = arrangementTerm.getAvailableSpots();

        if (availableSpots > 0 && availableSpots <= 3) {
            labels.add("Još " + availableSpots + " mesta");
        }

        return labels;
    }

    private boolean appliesToArrangement(PricingRule rule, Arrangement arrangement) {
        if (rule.getArrangement() == null) {
            return true;
        }

        return rule.getArrangement().getId().equals(arrangement.getId());
    }

    private boolean shouldApplyRule(PricingRule rule, ArrangementTerm arrangementTerm) {
        if (rule.getType() == PricingRuleType.SEASON) {
            return isSeasonRuleApplicable(rule, arrangementTerm);
        }

        if (rule.getType() == PricingRuleType.OCCUPANCY) {
            return isOccupancyRuleApplicable(rule, arrangementTerm);
        }

        if (rule.getType() == PricingRuleType.LAST_MINUTE) {
            return isLastMinuteRuleApplicable(rule, arrangementTerm);
        }

        if (rule.getType() == PricingRuleType.EARLY_BOOKING) {
            return isEarlyBookingRuleApplicable(rule, arrangementTerm);
        }

        return false;
    }

    private boolean isSeasonRuleApplicable(PricingRule rule, ArrangementTerm arrangementTerm) {
        LocalDate startDate = arrangementTerm.getTerm().getStartDate();

        return rule.getSeasonStart() != null
                && rule.getSeasonEnd() != null
                && !startDate.isBefore(rule.getSeasonStart())
                && !startDate.isAfter(rule.getSeasonEnd());
    }

    private boolean isOccupancyRuleApplicable(PricingRule rule, ArrangementTerm arrangementTerm) {
        if (rule.getMinOccupancyPercent() == null || arrangementTerm.getCapacity() == 0) {
            return false;
        }

        int occupancyPercent =
                arrangementTerm.getReservedSpots() * 100 / arrangementTerm.getCapacity();

        return occupancyPercent >= rule.getMinOccupancyPercent();
    }

    private boolean isLastMinuteRuleApplicable(PricingRule rule, ArrangementTerm arrangementTerm) {
        if (rule.getMaxDaysBeforeStart() == null) {
            return false;
        }

        long daysBeforeStart = ChronoUnit.DAYS.between(
                LocalDate.now(),
                arrangementTerm.getTerm().getStartDate()
        );

        return daysBeforeStart >= 0
                && daysBeforeStart <= rule.getMaxDaysBeforeStart();
    }

    private boolean isEarlyBookingRuleApplicable(PricingRule rule, ArrangementTerm arrangementTerm) {
        if (rule.getMinDaysBeforeStart() == null) {
            return false;
        }

        long daysBeforeStart = ChronoUnit.DAYS.between(
                LocalDate.now(),
                arrangementTerm.getTerm().getStartDate()
        );

        return daysBeforeStart >= rule.getMinDaysBeforeStart();
    }
}