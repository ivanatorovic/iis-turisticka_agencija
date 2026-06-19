package com.example.turisticka_agencija.repository;

import com.example.turisticka_agencija.model.PricingRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PricingRuleRepository extends JpaRepository<PricingRule, Long> {

    List<PricingRule> findByActiveTrue();
}