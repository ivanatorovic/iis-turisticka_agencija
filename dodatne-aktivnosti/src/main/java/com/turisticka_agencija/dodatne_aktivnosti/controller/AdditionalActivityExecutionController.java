package com.turisticka_agencija.dodatne_aktivnosti.controller;

import com.turisticka_agencija.dodatne_aktivnosti.dto.AdditionalActivityExecutionDTO;
import com.turisticka_agencija.dodatne_aktivnosti.model.AdditionalActivityExecution;
import com.turisticka_agencija.dodatne_aktivnosti.service.IAdditionalActivityExecutionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/executions")
public class AdditionalActivityExecutionController {

    private final IAdditionalActivityExecutionService executionService;

    public AdditionalActivityExecutionController(IAdditionalActivityExecutionService executionService) {
        this.executionService = executionService;
    }

    @GetMapping
    public ResponseEntity<List<AdditionalActivityExecution>> findAll() {
        return new ResponseEntity<>(executionService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdditionalActivityExecution> findById(@PathVariable Long id) {
        return new ResponseEntity<>(executionService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AdditionalActivityExecution> save(@RequestBody AdditionalActivityExecutionDTO dto) {
        return new ResponseEntity<>(executionService.save(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdditionalActivityExecution> update(@PathVariable Long id,
                                                              @RequestBody AdditionalActivityExecutionDTO dto) {
        return new ResponseEntity<>(executionService.update(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        executionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}