package com.turisticka_agencija.dodatne_aktivnosti.model;

import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.time.LocalDateTime;

@RelationshipProperties
public class Registration {

    @RelationshipId
    private Long id;

    private Long registrationId;

    private LocalDateTime registrationDate;

    private Integer numberOfPeople;

    private String status;

    @TargetNode
    private AdditionalActivityExecution execution;

    public Registration() {
    }

    public Registration(
            Long registrationId,
            AdditionalActivityExecution execution,
            LocalDateTime registrationDate,
            Integer numberOfPeople,
            String status
    ) {
        this.registrationId = registrationId;
        this.execution = execution;
        this.registrationDate = registrationDate;
        this.numberOfPeople = numberOfPeople;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(Long registrationId) {
        this.registrationId = registrationId;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AdditionalActivityExecution getExecution() {
        return execution;
    }

    public void setExecution(AdditionalActivityExecution execution) {
        this.execution = execution;
    }
}