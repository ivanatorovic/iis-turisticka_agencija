package com.example.turisticka_agencija.repository;

import com.example.turisticka_agencija.model.ArrangementTerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArrangementTermRepository
        extends JpaRepository<ArrangementTerm, Long> {

    List<ArrangementTerm> findByArrangementId(Long arrangementId);

    @Query("""
        SELECT at
        FROM ArrangementTerm at
        JOIN FETCH at.arrangement a
        JOIN FETCH a.destination
        JOIN FETCH at.term
        ORDER BY at.term.startDate ASC
    """)
    List<ArrangementTerm> findAllWithArrangementAndTerm();
}