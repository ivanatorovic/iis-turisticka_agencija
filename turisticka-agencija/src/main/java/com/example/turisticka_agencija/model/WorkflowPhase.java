package com.example.turisticka_agencija.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "workflow_phase")
public class WorkflowPhase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private WorkflowPhaseName name;

    @Enumerated(EnumType.STRING)
    private WorkflowPhaseType type;

    @ManyToOne
    @JoinColumn(name = "workflow_id", nullable = false)
    @JsonIgnore
    private Workflow workflow;

    public WorkflowPhase() {
    }

    public Long getId() {
        return id;
    }

    public WorkflowPhaseName getName() {
        return name;
    }

    public void setName(WorkflowPhaseName name) {
        this.name = name;
    }

    public WorkflowPhaseType getType() {
        return type;
    }

    public void setType(WorkflowPhaseType type) {
        this.type = type;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }
}