package com.turisticka_agencija.dodatne_aktivnosti.service;

import com.turisticka_agencija.dodatne_aktivnosti.model.Arrangement;

import java.util.List;

public interface IArrangementService {
    List<Arrangement> findAll();
    Arrangement findById(Long id);
    Arrangement save(Arrangement arrangement);
    Arrangement update(Long id, Arrangement arrangement);
    void delete(Long id);
}