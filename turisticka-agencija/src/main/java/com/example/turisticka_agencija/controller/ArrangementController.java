package com.example.turisticka_agencija.controller;

import com.example.turisticka_agencija.dto.ArrangementSearchRequest;
import com.example.turisticka_agencija.model.Arrangement;
import com.example.turisticka_agencija.model.Term;
import com.example.turisticka_agencija.repository.ArrangementRepository;
import com.example.turisticka_agencija.repository.TermRepository;
import com.example.turisticka_agencija.service.ArrangementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/arrangements")
public class ArrangementController {

    private final ArrangementService arrangementService;
    private final ArrangementRepository arrangementRepository;
    private final TermRepository termRepository;

    public ArrangementController(ArrangementService arrangementService,
                                 ArrangementRepository arrangementRepository,
                                 TermRepository termRepository) {
        this.arrangementService = arrangementService;
        this.arrangementRepository = arrangementRepository;
        this.termRepository = termRepository;
    }

    @GetMapping
    public List<Arrangement> getAllArrangements() {
        return arrangementService.getAllArrangements();
    }

    @PostMapping
    public Arrangement createArrangement(@RequestBody Arrangement arrangement) {
        return arrangementService.createArrangement(arrangement);
    }

    @PostMapping("/search")
    public List<Arrangement> searchArrangements(@RequestBody ArrangementSearchRequest request) {
        return arrangementService.searchArrangements(request);
    }

    @PutMapping("/{arrangementId}/terms/{termId}")
    public Arrangement addTermToArrangement(@PathVariable Long arrangementId,
                                            @PathVariable Long termId) {

        Arrangement arrangement = arrangementRepository.findById(arrangementId)
                .orElseThrow(() -> new RuntimeException("Arrangement not found"));

        Term term = termRepository.findById(termId)
                .orElseThrow(() -> new RuntimeException("Term not found"));

        arrangement.getTerms().add(term);

        return arrangementRepository.save(arrangement);
    }
}