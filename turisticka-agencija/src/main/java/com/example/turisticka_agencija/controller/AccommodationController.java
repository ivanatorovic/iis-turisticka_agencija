package com.example.turisticka_agencija.controller;

import com.example.turisticka_agencija.model.Accommodation;
import com.example.turisticka_agencija.repository.AccommodationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accommodations")
public class AccommodationController {

    private final AccommodationRepository accommodationRepository;

    public AccommodationController(AccommodationRepository accommodationRepository) {
        this.accommodationRepository = accommodationRepository;
    }

    @GetMapping
    public List<Accommodation> getAll() {
        return accommodationRepository.findAll();
    }
}