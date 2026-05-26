package com.example.turisticka_agencija.controller;

import com.example.turisticka_agencija.dto.DokumentacijaZalbeResponse;
import com.example.turisticka_agencija.service.DokumentacijaZalbeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/zalbe")
public class DokumentacijaZalbeController {

    private final DokumentacijaZalbeService dokumentacijaZalbeService;

    public DokumentacijaZalbeController(DokumentacijaZalbeService dokumentacijaZalbeService) {
        this.dokumentacijaZalbeService = dokumentacijaZalbeService;
    }

    @GetMapping("/{zalbaId}/dokumentacija")
    public DokumentacijaZalbeResponse getDokumentacijaZaZalbu(@PathVariable Long zalbaId) {
        return dokumentacijaZalbeService.getDokumentacijaZaZalbu(zalbaId);
    }
}