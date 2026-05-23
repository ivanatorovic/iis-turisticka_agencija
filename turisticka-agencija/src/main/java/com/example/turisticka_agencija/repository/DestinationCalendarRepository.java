package com.example.turisticka_agencija.repository;

import com.example.turisticka_agencija.model.DestinationCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DestinationCalendarRepository extends JpaRepository<DestinationCalendar, Long> {

    List<DestinationCalendar> findByDestinationId(Long destinationId);
}