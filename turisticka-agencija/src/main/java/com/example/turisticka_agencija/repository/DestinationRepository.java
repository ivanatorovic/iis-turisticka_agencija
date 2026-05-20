package com.example.turisticka_agencija.repository;

import com.example.turisticka_agencija.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
    List<Destination> findByCategory(String category);

    List<Destination> findByCountry(String country);

    List<Destination> findByCategoryAndCountry(String category, String country);
}
