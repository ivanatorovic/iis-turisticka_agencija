package com.turisticka_agencija.dodatne_aktivnosti.service;

import com.turisticka_agencija.dodatne_aktivnosti.dto.AdditionalActivityExecutionDTO;
import com.turisticka_agencija.dodatne_aktivnosti.model.AdditionalActivityExecution;

import java.util.List;

public interface IAdditionalActivityExecutionService {

    List<AdditionalActivityExecution> findAll();

    AdditionalActivityExecution findById(Long id);

    AdditionalActivityExecution save(AdditionalActivityExecutionDTO dto);

    AdditionalActivityExecution update(Long id, AdditionalActivityExecutionDTO dto);

    void delete(Long id);
}