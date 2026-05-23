package com.example.turisticka_agencija.repository;

import com.example.turisticka_agencija.model.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
}