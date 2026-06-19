package com.example.turisticka_agencija.controller;

import com.example.turisticka_agencija.dto.PricingRuleRequest;
import com.example.turisticka_agencija.model.PricingRule;
import com.example.turisticka_agencija.service.PricingRuleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pricing-rules")
public class PricingRuleController {

    private final PricingRuleService pricingRuleService;

    public PricingRuleController(PricingRuleService pricingRuleService) {
        this.pricingRuleService = pricingRuleService;
    }

    @GetMapping
    public List<PricingRule> getAllRules() {
        return pricingRuleService.getAllRules();
    }

    @PostMapping
    public PricingRule createRule(@RequestBody PricingRuleRequest request) {
        return pricingRuleService.createRule(request);
    }

    @PutMapping("/{id}")
    public PricingRule updateRule(@PathVariable Long id,
                                  @RequestBody PricingRuleRequest request) {
        return pricingRuleService.updateRule(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteRule(@PathVariable Long id) {
        pricingRuleService.deleteRule(id);
    }
}