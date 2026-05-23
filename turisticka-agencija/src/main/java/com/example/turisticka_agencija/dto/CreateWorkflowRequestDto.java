package com.example.turisticka_agencija.dto;

import com.example.turisticka_agencija.model.WorkflowPhaseName;

import java.util.List;

public class CreateWorkflowRequestDto {

    private String name;

    private List<WorkflowPhaseName> phases;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WorkflowPhaseName> getPhases() {
        return phases;
    }

    public void setPhases(List<WorkflowPhaseName> phases) {
        this.phases = phases;
    }
}