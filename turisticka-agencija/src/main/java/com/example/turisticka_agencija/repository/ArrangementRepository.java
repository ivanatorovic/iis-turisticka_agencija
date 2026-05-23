package com.example.turisticka_agencija.repository;

import com.example.turisticka_agencija.model.Arrangement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArrangementRepository extends JpaRepository<Arrangement, Long> {
}