package com.example.turisticka_agencija.repository;

import com.example.turisticka_agencija.model.AdditionalActivityExecution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdditionalActivityExecutionRepository
        extends JpaRepository<AdditionalActivityExecution, Long> {

    List<AdditionalActivityExecution> findByArrangementTermId(Long arrangementTermId);
}