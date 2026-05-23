package com.example.turisticka_agencija.controller;

import com.example.turisticka_agencija.model.Transport;
import com.example.turisticka_agencija.repository.TransportRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transports")
public class TransportController {

    private final TransportRepository transportRepository;

    public TransportController(TransportRepository transportRepository) {
        this.transportRepository = transportRepository;
    }

    @GetMapping
    public List<Transport> getAll() {
        return transportRepository.findAll();
    }
}