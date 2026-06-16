package com.example.turisticka_agencija.service;

import com.example.turisticka_agencija.dto.PricingRuleRequest;
import com.example.turisticka_agencija.model.Arrangement;
import com.example.turisticka_agencija.model.PricingRule;
import com.example.turisticka_agencija.model.PricingRuleType;
import com.example.turisticka_agencija.repository.ArrangementRepository;
import com.example.turisticka_agencija.repository.PricingRuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PricingRuleService {

    private final PricingRuleRepository pricingRuleRepository;
    private final ArrangementRepository arrangementRepository;

    public PricingRuleService(PricingRuleRepository pricingRuleRepository,
                              ArrangementRepository arrangementRepository) {
        this.pricingRuleRepository = pricingRuleRepository;
        this.arrangementRepository = arrangementRepository;
    }

    public List<PricingRule> getAllRules() {
        return pricingRuleRepository.findAll();
    }

    public PricingRule createRule(PricingRuleRequest request) {
        validateRequest(request);

        PricingRule rule = new PricingRule();

        fillRuleFromRequest(rule, request);

        return pricingRuleRepository.save(rule);
    }

    public PricingRule updateRule(Long id, PricingRuleRequest request) {
        validateRequest(request);

        PricingRule rule = pricingRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pricing rule not found"));

        fillRuleFromRequest(rule, request);

        return pricingRuleRepository.save(rule);
    }

    public void deleteRule(Long id) {
        pricingRuleRepository.deleteById(id);
    }

    private void fillRuleFromRequest(PricingRule rule, PricingRuleRequest request) {
        rule.setName(request.getName());
        rule.setType(request.getType());
        rule.setPercentage(request.getPercentage());
        rule.setActive(request.isActive());

        rule.setSeasonStart(request.getSeasonStart());
        rule.setSeasonEnd(request.getSeasonEnd());

        rule.setMinOccupancyPercent(request.getMinOccupancyPercent());

        rule.setMaxDaysBeforeStart(request.getMaxDaysBeforeStart());
        rule.setMinDaysBeforeStart(request.getMinDaysBeforeStart());

        if (request.getArrangementId() != null) {
            Arrangement arrangement = arrangementRepository.findById(request.getArrangementId())
                    .orElseThrow(() -> new RuntimeException("Arrangement not found"));

            rule.setArrangement(arrangement);
        } else {
            rule.setArrangement(null);
        }
    }

    private void validateRequest(PricingRuleRequest request) {
        if (request.getType() == null) {
            throw new RuntimeException("Rule type is required");
        }

        if (request.getType() == PricingRuleType.SEASON) {
            if (request.getArrangementId() == null) {
                throw new RuntimeException("For season rule, arrangement is required");
            }

            if (request.getSeasonStart() == null || request.getSeasonEnd() == null) {
                throw new RuntimeException("Season start and end are required");
            }
        }

        if (request.getType() == PricingRuleType.OCCUPANCY) {
            if (request.getMinOccupancyPercent() == null) {
                throw new RuntimeException("Minimum occupancy percent is required");
            }
        }

        if (request.getType() == PricingRuleType.LAST_MINUTE) {
            if (request.getMaxDaysBeforeStart() == null) {
                throw new RuntimeException("Maximum days before start is required");
            }
        }

        if (request.getType() == PricingRuleType.EARLY_BOOKING) {
            if (request.getMinDaysBeforeStart() == null) {
                throw new RuntimeException("Minimum days before start is required");
            }
        }
    }
}