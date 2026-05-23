package com.example.turisticka_agencija.controller;

import com.example.turisticka_agencija.model.PraviloKategorizacije;
import com.example.turisticka_agencija.service.PraviloKategorizacijeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pravila-kategorizacije")
@CrossOrigin(origins = "*")
public class PraviloKategorizacijeController {

    private final PraviloKategorizacijeService praviloService;

    public PraviloKategorizacijeController(PraviloKategorizacijeService praviloService) {
        this.praviloService = praviloService;
    }

    @PostMapping
    public PraviloKategorizacije kreirajPravilo(@RequestBody PraviloKategorizacije pravilo) {
        return praviloService.kreirajPravilo(pravilo);
    }

    @GetMapping
    public List<PraviloKategorizacije> pronadjiSvaPravila() {
        return praviloService.pronadjiSvaPravila();
    }

    @PutMapping("/{id}")
    public PraviloKategorizacije izmeniPravilo(
            @PathVariable Long id,
            @RequestBody PraviloKategorizacije pravilo
    ) {
        return praviloService.izmeniPravilo(id, pravilo);
    }

    @DeleteMapping("/{id}")
    public void obrisiPravilo(@PathVariable Long id) {
        praviloService.obrisiPravilo(id);
    }
}