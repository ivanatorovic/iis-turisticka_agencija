package com.turisticka_agencija.dodatne_aktivnosti.controller;

import com.turisticka_agencija.dodatne_aktivnosti.model.Arrangement;
import com.turisticka_agencija.dodatne_aktivnosti.service.IArrangementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/arrangements")
public class ArrangementController {

    private final IArrangementService arrangementService;

    public ArrangementController(IArrangementService arrangementService) {
        this.arrangementService = arrangementService;
    }

    @GetMapping
    public ResponseEntity<List<Arrangement>> findAll() {
        return new ResponseEntity<>(arrangementService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Arrangement> findById(@PathVariable Long id) {
        return new ResponseEntity<>(arrangementService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Arrangement> save(@RequestBody Arrangement arrangement) {
        return new ResponseEntity<>(arrangementService.save(arrangement), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Arrangement> update(@PathVariable Long id,
                                              @RequestBody Arrangement arrangement) {
        return new ResponseEntity<>(arrangementService.update(id, arrangement), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        arrangementService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}