package com.example.turisticka_agencija.repository;

import com.example.turisticka_agencija.model.ArrangementTerm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArrangementTermRepository
        extends JpaRepository<ArrangementTerm, Long> {

    List<ArrangementTerm> findByArrangementId(Long arrangementId);
}