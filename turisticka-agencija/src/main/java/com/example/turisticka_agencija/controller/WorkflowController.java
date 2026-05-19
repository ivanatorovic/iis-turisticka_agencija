package com.example.turisticka_agencija.controller;

import com.example.turisticka_agencija.dto.CreateWorkflowRequestDto;
import com.example.turisticka_agencija.dto.CreateWorkflowRequestDto;
import com.example.turisticka_agencija.model.Workflow;
import com.example.turisticka_agencija.service.WorkflowService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.example.turisticka_agencija.dto.SendWorkflowRequestDto;

import java.util.List;

@RestController
@RequestMapping("/api/workflows")
public class WorkflowController {

    private final WorkflowService workflowService;

    public WorkflowController(
            WorkflowService workflowService
    ) {
        this.workflowService = workflowService;
    }

    @PostMapping
    public ResponseEntity<Workflow> createWorkflow(
            Authentication authentication,
            @RequestBody CreateWorkflowRequestDto request
    ) {

        Workflow workflow = workflowService.createWorkflow(
                authentication.getName(),
                request
        );

        return ResponseEntity.ok(workflow);
    }

    @GetMapping
    public ResponseEntity<List<Workflow>> getAllWorkflows() {

        return ResponseEntity.ok(
                workflowService.getAllWorkflows()
        );
    }

    @GetMapping("/my")
    public ResponseEntity<List<Workflow>> getMyWorkflows(
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                workflowService.getMyWorkflows(
                        authentication.getName()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Workflow> getById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                workflowService.getById(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkflow(
            @PathVariable Long id
    ) {

        workflowService.deleteWorkflow(id);

        return ResponseEntity.ok(
                "Workflow deleted successfully"
        );
    }

    @DeleteMapping("/{workflowId}/phases/{phaseId}")
    public ResponseEntity<Workflow> deletePhase(
            @PathVariable Long workflowId,
            @PathVariable Long phaseId
    ) {

        return ResponseEntity.ok(
                workflowService.deletePhase(
                        workflowId,
                        phaseId
                )
        );
    }

    @PutMapping("/{id}/send-to-manager")
    public ResponseEntity<Workflow> sendToManager(
            @PathVariable Long id,
            @RequestBody SendWorkflowRequestDto request
    ) {
        return ResponseEntity.ok(
                workflowService.sendToManager(id, request.getManagerUsername())
        );
    }

    @GetMapping("/received")
    public ResponseEntity<List<Workflow>> getReceivedWorkflows(
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                workflowService.getReceivedWorkflows(authentication.getName())
        );
    }
}