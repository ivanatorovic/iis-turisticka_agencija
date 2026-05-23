package com.example.turisticka_agencija.controller;

import com.example.turisticka_agencija.dto.ArrangementSearchRequest;
import com.example.turisticka_agencija.model.Arrangement;
import com.example.turisticka_agencija.service.ArrangementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/arrangements")
public class ArrangementController {

    private final ArrangementService arrangementService;

    public ArrangementController(ArrangementService arrangementService) {
        this.arrangementService = arrangementService;
    }

    @GetMapping
    public List<Arrangement> getAllArrangements() {
        return arrangementService.getAllArrangements();
    }

    @GetMapping("/{id}")
    public Arrangement getArrangementById(@PathVariable Long id) {
        return arrangementService.getArrangementById(id);
    }

    @PostMapping
    public Arrangement createArrangement(@RequestBody Arrangement arrangement) {
        return arrangementService.createArrangement(arrangement);
    }

    @PostMapping("/search")
    public List<Arrangement> searchArrangements(@RequestBody ArrangementSearchRequest request) {
        return arrangementService.searchArrangements(request);
    }
}