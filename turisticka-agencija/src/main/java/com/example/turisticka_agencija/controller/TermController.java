package com.example.turisticka_agencija.controller;

import com.example.turisticka_agencija.model.Term;
import com.example.turisticka_agencija.repository.TermRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/terms")
public class TermController {

    private final TermRepository termRepository;

    public TermController(TermRepository termRepository) {
        this.termRepository = termRepository;
    }

    @GetMapping
    public List<Term> getAllTerms() {
        return termRepository.findAll();
    }

    @PostMapping
    public Term createTerm(@RequestBody Term term) {
        return termRepository.save(term);
    }
}