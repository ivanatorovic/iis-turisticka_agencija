package com.example.turisticka_agencija.controller;

import com.example.turisticka_agencija.dto.ArrangementTermResponseDto;
import com.example.turisticka_agencija.service.ArrangementTermService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/arrangement-terms")
public class ArrangementTermController {

    private final ArrangementTermService arrangementTermService;

    public ArrangementTermController(ArrangementTermService arrangementTermService) {
        this.arrangementTermService = arrangementTermService;
    }

    @GetMapping
    public List<ArrangementTermResponseDto> getAllArrangementTerms() {
        return arrangementTermService.getAllArrangementTerms();
    }
}