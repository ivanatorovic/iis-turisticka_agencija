package com.example.turisticka_agencija.repository;

import com.example.turisticka_agencija.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}