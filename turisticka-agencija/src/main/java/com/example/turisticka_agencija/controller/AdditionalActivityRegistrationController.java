package com.example.turisticka_agencija.controller;

import com.example.turisticka_agencija.dto.AdditionalActivityParticipantResponse;
import com.example.turisticka_agencija.dto.AdditionalActivityRegistrationRequest;
import com.example.turisticka_agencija.dto.AdditionalActivityRegistrationResponse;
import com.example.turisticka_agencija.dto.AdditionalActivityRegistrationUpdateRequest;
import com.example.turisticka_agencija.service.AdditionalActivityRegistrationService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/additional-activity-registrations")
public class AdditionalActivityRegistrationController {

    private final AdditionalActivityRegistrationService registrationService;

    public AdditionalActivityRegistrationController(
            AdditionalActivityRegistrationService registrationService
    ) {
        this.registrationService = registrationService;
    }

    @PostMapping("/execution/{executionId}")
    public AdditionalActivityRegistrationResponse register(
            @PathVariable Long executionId,
            @RequestBody AdditionalActivityRegistrationRequest request,
            Principal principal
    ) {
        return registrationService.register(executionId, request, principal);
    }

    @GetMapping("/my")
    public List<AdditionalActivityRegistrationResponse> getMyRegistrations(
            Principal principal
    ) {
        return registrationService.getMyRegistrations(principal);
    }

    @PutMapping("/{registrationId}/cancel")
    public void cancelRegistration(
            @PathVariable Long registrationId,
            Principal principal
    ) {
        registrationService.cancelRegistration(registrationId, principal);
    }

    @GetMapping("/execution/{executionId}/participants")
    public List<AdditionalActivityParticipantResponse> getParticipantsForExecution(
            @PathVariable Long executionId,
            Principal principal
    ) {
        return registrationService.getParticipantsForExecution(executionId, principal);
    }

    @PutMapping("/{registrationId}")
    public AdditionalActivityRegistrationResponse updateRegistration(
            @PathVariable Long registrationId,
            @RequestBody(required = false) AdditionalActivityRegistrationUpdateRequest request,
            Principal principal
    ) {
        return registrationService.updateRegistration(registrationId, request, principal);
    }
}