package com.example.turisticka_agencija.service;

import com.example.turisticka_agencija.dto.AdditionalActivityParticipantResponse;
import com.example.turisticka_agencija.dto.AdditionalActivityRegistrationRequest;
import com.example.turisticka_agencija.dto.AdditionalActivityRegistrationResponse;
import com.example.turisticka_agencija.exception.BadRequestException;
import com.example.turisticka_agencija.model.*;
import com.example.turisticka_agencija.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdditionalActivityRegistrationService {

    private final AdditionalActivityRegistrationRepository registrationRepository;
    private final AdditionalActivityExecutionRepository executionRepository;
    private final AdditionalActivityPriceListRepository priceListRepository;
    private final UserRepository userRepository;

    public AdditionalActivityRegistrationService(
            AdditionalActivityRegistrationRepository registrationRepository,
            AdditionalActivityExecutionRepository executionRepository,
            AdditionalActivityPriceListRepository priceListRepository,
            UserRepository userRepository
    ) {
        this.registrationRepository = registrationRepository;
        this.executionRepository = executionRepository;
        this.priceListRepository = priceListRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public AdditionalActivityRegistrationResponse register(
            Long executionId,
            AdditionalActivityRegistrationRequest request,
            Principal principal
    ) {
        User user = getAuthenticatedUser(principal);

        if (request == null || request.getNumberOfParticipants() == null) {
            throw new BadRequestException("Morate uneti broj prijavljenih osoba");
        }

        if (request.getNumberOfParticipants() <= 0) {
            throw new BadRequestException("Broj prijavljenih osoba mora biti veći od 0");
        }

        AdditionalActivityExecution execution = executionRepository.findById(executionId)
                .orElseThrow(() -> new BadRequestException("Dodatna aktivnost nije pronađena"));

        if (registrationRepository.existsByUserIdAndAdditionalActivityExecutionIdAndStatus(
                user.getId(),
                executionId,
                AdditionalActivityRegistrationStatus.ACTIVE
        )) {
            throw new BadRequestException("Već ste prijavljeni na ovu dodatnu aktivnost");
        }

        if (execution.getAvailableSpots() < request.getNumberOfParticipants()) {
            throw new BadRequestException("Nema dovoljno slobodnih mesta za ovu aktivnost");
        }

        AdditionalActivityRegistration registration = new AdditionalActivityRegistration();
        registration.setUser(user);
        registration.setAdditionalActivityExecution(execution);
        registration.setNumberOfParticipants(request.getNumberOfParticipants());
        registration.setRegistrationDate(LocalDateTime.now());
        registration.setStatus(AdditionalActivityRegistrationStatus.ACTIVE);

        execution.setReservedSpots(
                execution.getReservedSpots() + request.getNumberOfParticipants()
        );

        executionRepository.save(execution);
        registration = registrationRepository.save(registration);

        return mapToResponse(registration);
    }

    public List<AdditionalActivityRegistrationResponse> getMyRegistrations(Principal principal) {
        User user = getAuthenticatedUser(principal);

        return registrationRepository.findByUserIdOrderByRegistrationDateDesc(user.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public void cancelRegistration(Long registrationId, Principal principal) {
        User user = getAuthenticatedUser(principal);

        AdditionalActivityRegistration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new BadRequestException("Prijava nije pronađena"));

        if (!registration.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Ne možete otkazati tuđu prijavu");
        }

        if (registration.getStatus() == AdditionalActivityRegistrationStatus.CANCELLED) {
            throw new BadRequestException("Prijava je već otkazana");
        }

        AdditionalActivityExecution execution = registration.getAdditionalActivityExecution();

        execution.setReservedSpots(
                Math.max(0, execution.getReservedSpots() - registration.getNumberOfParticipants())
        );

        registration.setStatus(AdditionalActivityRegistrationStatus.CANCELLED);

        executionRepository.save(execution);
        registrationRepository.save(registration);
    }

    public List<AdditionalActivityParticipantResponse> getParticipantsForExecution(
            Long executionId,
            Principal principal
    ) {
        User user = getAuthenticatedUser(principal);

        if (user.getRole() != Role.GUIDE && user.getRole() != Role.MANAGER) {
            throw new BadRequestException("Nemate dozvolu za pregled učesnika");
        }

        return registrationRepository.findByAdditionalActivityExecutionId(executionId)
                .stream()
                .map(this::mapToParticipantResponse)
                .toList();
    }

    private AdditionalActivityParticipantResponse mapToParticipantResponse(
            AdditionalActivityRegistration registration
    ) {
        User user = registration.getUser();

        return new AdditionalActivityParticipantResponse(
                registration.getId(),
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getContact(),
                registration.getNumberOfParticipants(),
                registration.getRegistrationDate(),
                registration.getStatus()
        );
    }

    private AdditionalActivityRegistrationResponse mapToResponse(
            AdditionalActivityRegistration registration
    ) {
        AdditionalActivityExecution execution = registration.getAdditionalActivityExecution();
        AdditionalActivity activity = execution.getAdditionalActivity();

        double price = priceListRepository
                .findFirstByAdditionalActivityExecutionIdOrderByIdDesc(execution.getId())
                .map(AdditionalActivityPriceList::getPrice)
                .orElse(0.0);

        return new AdditionalActivityRegistrationResponse(
                registration.getId(),
                execution.getId(),
                activity.getName(),
                activity.getDescription(),
                activity.getLocation(),
                activity.getImageUrl(),
                execution.getActivityTerm().getDate(),
                execution.getActivityTerm().getStartTime(),
                execution.getDurationMinutes(),
                registration.getNumberOfParticipants(),
                price,
                registration.getRegistrationDate(),
                registration.getStatus()
        );
    }

    private User getAuthenticatedUser(Principal principal) {
        if (principal == null || principal.getName() == null) {
            throw new BadRequestException("Korisnik nije prijavljen");
        }

        return userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new BadRequestException("Ulogovani korisnik nije pronađen"));
    }
}