package com.example.turisticka_agencija.dto;

import com.example.turisticka_agencija.model.AdditionalActivityRegistrationStatus;

import java.time.LocalDateTime;

public class AdditionalActivityParticipantResponse {

    private Long registrationId;

    private Long userId;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String contact;

    private int numberOfParticipants;
    private LocalDateTime registrationDate;

    private AdditionalActivityRegistrationStatus status;

    public AdditionalActivityParticipantResponse(
            Long registrationId,
            Long userId,
            String firstName,
            String lastName,
            String username,
            String email,
            String contact,
            int numberOfParticipants,
            LocalDateTime registrationDate,
            AdditionalActivityRegistrationStatus status
    ) {
        this.registrationId = registrationId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.contact = contact;
        this.numberOfParticipants = numberOfParticipants;
        this.registrationDate = registrationDate;
        this.status = status;
    }

    public Long getRegistrationId() {
        return registrationId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
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
}