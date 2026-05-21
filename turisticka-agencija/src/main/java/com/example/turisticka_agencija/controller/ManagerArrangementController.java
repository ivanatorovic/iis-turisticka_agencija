package com.example.turisticka_agencija.controller;

import com.example.turisticka_agencija.dto.ManagerArrangementRequestDto;
import com.example.turisticka_agencija.model.ManagerArrangement;
import com.example.turisticka_agencija.service.ManagerArrangementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager-arrangements")
public class ManagerArrangementController {

    private final ManagerArrangementService managerArrangementService;

    public ManagerArrangementController(ManagerArrangementService managerArrangementService) {
        this.managerArrangementService = managerArrangementService;
    }

    @GetMapping("/manager/{managerId}")
    public List<ManagerArrangement> getByManager(@PathVariable Long managerId) {
        return managerArrangementService.getByManager(managerId);
    }

    @GetMapping("/director/pending")
    public List<ManagerArrangement> getPendingForDirector() {
        return managerArrangementService.getPendingForDirector();
    }

    @GetMapping("/{id}")
    public ManagerArrangement getById(@PathVariable Long id) {
        return managerArrangementService.getById(id);
    }

    @PostMapping
    public ManagerArrangement create(@RequestBody ManagerArrangementRequestDto request) {
        return managerArrangementService.create(request);
    }

    @PutMapping("/{id}")
    public ManagerArrangement update(
            @PathVariable Long id,
            @RequestBody ManagerArrangementRequestDto request
    ) {
        return managerArrangementService.update(id, request);
    }

    @PatchMapping("/{id}/send-to-director")
    public ManagerArrangement sendToDirector(@PathVariable Long id) {
        return managerArrangementService.sendToDirector(id);
    }

    @PatchMapping("/{id}/approve")
    public ManagerArrangement approve(@PathVariable Long id) {
        return managerArrangementService.approve(id);
    }

    @PatchMapping("/{id}/reject")
    public ManagerArrangement reject(@PathVariable Long id) {
        return managerArrangementService.reject(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        managerArrangementService.delete(id);
    }
}