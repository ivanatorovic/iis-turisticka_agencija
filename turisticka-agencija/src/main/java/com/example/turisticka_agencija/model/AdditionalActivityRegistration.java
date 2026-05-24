package com.example.turisticka_agencija.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AdditionalActivityRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private AdditionalActivityExecution additionalActivityExecution;

    private int numberOfParticipants;

    private LocalDateTime registrationDate;

    @Enumerated(EnumType.STRING)
    private AdditionalActivityRegistrationStatus status;

    public AdditionalActivityRegistration() {
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public AdditionalActivityExecution getAdditionalActivityExecution() {
        return additionalActivityExecution;
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public AdditionalActivityRegistrationStatus getStatus() {
        return status;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAdditionalActivityExecution(AdditionalActivityExecution additionalActivityExecution) {
        this.additionalActivityExecution = additionalActivityExecution;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setStatus(AdditionalActivityRegistrationStatus status) {
        this.status = status;
    }
}