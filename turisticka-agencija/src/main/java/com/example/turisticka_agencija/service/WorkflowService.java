package com.example.turisticka_agencija.service;

import com.example.turisticka_agencija.dto.CreateWorkflowRequestDto;
import com.example.turisticka_agencija.dto.CreateWorkflowRequestDto;
import com.example.turisticka_agencija.exception.BadRequestException;
import com.example.turisticka_agencija.model.*;
import com.example.turisticka_agencija.repository.WorkflowRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkflowService {

    private final WorkflowRepository workflowRepository;
    private final UserService userService;

    public WorkflowService(
            WorkflowRepository workflowRepository,
            UserService userService
    ) {
        this.workflowRepository = workflowRepository;
        this.userService = userService;
    }

    public Workflow createWorkflow(
            String username,
            CreateWorkflowRequestDto request
    ) {

        User admin = userService.findByUsername(username);

        if (admin.getRole() != Role.ADMIN) {
            throw new BadRequestException(
                    "Only admin can create workflows"
            );
        }

        Workflow workflow = new Workflow();
        workflow.setName(request.getName());
        workflow.setAdmin(admin);

        if (request.getPhases() != null) {

            for (WorkflowPhaseName phaseName : request.getPhases()) {

                WorkflowPhase phase = new WorkflowPhase();

                phase.setName(phaseName);
                phase.setType(WorkflowPhaseType.RECOMMENDED);
                phase.setWorkflow(workflow);

                workflow.getPhases().add(phase);
            }
        }

        return workflowRepository.save(workflow);
    }

    public List<Workflow> getAllWorkflows() {
        return workflowRepository.findAll();
    }

    public List<Workflow> getMyWorkflows(String username) {
        return workflowRepository.findByAdminUsername(username);
    }

    public Workflow getById(Long id) {

        return workflowRepository.findById(id)
                .orElseThrow(() ->
                        new BadRequestException(
                                "Workflow not found"
                        ));
    }

    public void deleteWorkflow(Long id) {

        Workflow workflow = workflowRepository.findById(id)
                .orElseThrow(() ->
                        new BadRequestException(
                                "Workflow not found"
                        ));

        workflowRepository.delete(workflow);
    }

    public Workflow deletePhase(
            Long workflowId,
            Long phaseId
    ) {

        Workflow workflow = workflowRepository.findById(workflowId)
                .orElseThrow(() ->
                        new BadRequestException(
                                "Workflow not found"
                        ));

        WorkflowPhase phaseToDelete = workflow.getPhases()
                .stream()
                .filter(phase -> phase.getId().equals(phaseId))
                .findFirst()
                .orElseThrow(() ->
                        new BadRequestException(
                                "Phase not found"
                        ));

        workflow.getPhases().remove(phaseToDelete);

        return workflowRepository.save(workflow);
    }

    public Workflow sendToManager(Long workflowId, String managerUsername) {
        Workflow workflow = workflowRepository.findById(workflowId)
                .orElseThrow(() -> new BadRequestException("Workflow not found"));

        User manager = userService.findByUsername(managerUsername);

        if (manager.getRole() != Role.MANAGER) {
            throw new BadRequestException("User is not a manager");
        }

        workflow.setManager(manager);

        return workflowRepository.save(workflow);
    }

    public List<Workflow> getReceivedWorkflows(String username) {
        return workflowRepository.findByManagerUsername(username);
    }
}