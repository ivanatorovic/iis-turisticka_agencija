package com.example.turisticka_agencija.service;

import com.example.turisticka_agencija.dto.*;
import com.example.turisticka_agencija.exception.BadRequestException;
import com.example.turisticka_agencija.model.*;
import com.example.turisticka_agencija.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdditionalActivityRegistrationService {

    private final AdditionalActivityRegistrationRepository registrationRepository;
    private final AdditionalActivityExecutionRepository executionRepository;
    private final AdditionalActivityPriceListRepository priceListRepository;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    public AdditionalActivityRegistrationService(
            AdditionalActivityRegistrationRepository registrationRepository,
            AdditionalActivityExecutionRepository executionRepository,
            AdditionalActivityPriceListRepository priceListRepository,
            UserRepository userRepository, RestTemplate restTemplate
    ) {
        this.registrationRepository = registrationRepository;
        this.executionRepository = executionRepository;
        this.priceListRepository = priceListRepository;
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
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

        syncRegistrationToRecommendationService(
                user.getId(),
                execution.getId(),
                registration.getId(),
                registration.getNumberOfParticipants(),
                registration.getStatus().name()
        );

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

        deleteRegistrationFromRecommendationService(
                user.getId(),
                execution.getId()
        );
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

    @Transactional
    public AdditionalActivityRegistrationResponse updateRegistration(
            Long registrationId,
            AdditionalActivityRegistrationUpdateRequest request,
            Principal principal
    ) {
        User user = getAuthenticatedUser(principal);

        if (request == null || request.getNumberOfParticipants() == null) {
            throw new BadRequestException("Morate uneti broj prijavljenih osoba");
        }

        if (request.getNumberOfParticipants() <= 0) {
            throw new BadRequestException("Broj prijavljenih osoba mora biti veći od 0");
        }

        AdditionalActivityRegistration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new BadRequestException("Prijava nije pronađena"));

        if (!registration.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Ne možete izmeniti tuđu prijavu");
        }

        if (registration.getStatus() != AdditionalActivityRegistrationStatus.ACTIVE) {
            throw new BadRequestException("Možete menjati samo aktivne prijave");
        }

        AdditionalActivityExecution execution = registration.getAdditionalActivityExecution();

        int oldParticipants = registration.getNumberOfParticipants();
        int newParticipants = request.getNumberOfParticipants();

        int difference = newParticipants - oldParticipants;

        if (difference > 0 && execution.getAvailableSpots() < difference) {
            throw new BadRequestException("Nema dovoljno slobodnih mesta za povećanje prijave");
        }

        execution.setReservedSpots(execution.getReservedSpots() + difference);
        registration.setNumberOfParticipants(newParticipants);

        executionRepository.save(execution);
        registration = registrationRepository.save(registration);

        return mapToResponse(registration);
    }

    private void syncRegistrationToRecommendationService(
            Long customerId,
            Long executionId,
            Long registrationId,
            Integer numberOfPeople,
            String status
    ) {
        String url =
                "http://dodatne-aktivnosti-service:8082/customers/"
                        + customerId
                        + "/registrations";

        RecommendationRegistrationRequest request =
                new RecommendationRegistrationRequest(
                        registrationId,
                        executionId,
                        numberOfPeople,
                        status
                );

        restTemplate.postForEntity(
                url,
                request,
                Void.class
        );
    }

    private void deleteRegistrationFromRecommendationService(
            Long customerId,
            Long executionId
    ) {
        String url =
                "http://dodatne-aktivnosti-service:8082/customers/"
                        + customerId
                        + "/registrations/"
                        + executionId;

        restTemplate.delete(url);
    }
}