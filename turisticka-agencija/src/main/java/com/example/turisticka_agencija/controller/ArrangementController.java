package com.example.turisticka_agencija.controller;

import com.example.turisticka_agencija.model.Arrangement;
import com.example.turisticka_agencija.model.Term;
import com.example.turisticka_agencija.repository.ArrangementRepository;
import com.example.turisticka_agencija.repository.TermRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/arrangements")
public class ArrangementController {

    private final ArrangementRepository arrangementRepository;
    private final TermRepository termRepository;
    public ArrangementController(ArrangementRepository arrangementRepository,
                                 TermRepository termRepository) {
        this.arrangementRepository = arrangementRepository;
        this.termRepository = termRepository;
    }

    @GetMapping
    public List<Arrangement> getAllArrangements() {
        return arrangementRepository.findAll();
    }

    @PostMapping
    public Arrangement createArrangement(@RequestBody Arrangement arrangement) {
        return arrangementRepository.save(arrangement);
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