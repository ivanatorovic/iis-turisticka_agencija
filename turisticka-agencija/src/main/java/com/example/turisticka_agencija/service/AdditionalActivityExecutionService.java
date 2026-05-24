package com.example.turisticka_agencija.service;

import com.example.turisticka_agencija.dto.AdditionalActivityExecutionRequest;
import com.example.turisticka_agencija.dto.AdditionalActivityExecutionResponse;
import com.example.turisticka_agencija.dto.AdditionalActivityRegistrationUpdateRequest;
import com.example.turisticka_agencija.exception.BadRequestException;
import com.example.turisticka_agencija.model.*;
import com.example.turisticka_agencija.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Service
public class AdditionalActivityExecutionService {

    private final AdditionalActivityExecutionRepository executionRepository;
    private final AdditionalActivityRepository additionalActivityRepository;
    private final ArrangementTermRepository arrangementTermRepository;
    private final ActivityTermRepository activityTermRepository;
    private final AdditionalActivityPriceListRepository priceListRepository;
    private final UserRepository userRepository;
    private final AdditionalActivityRegistrationRepository registrationRepository;

    public AdditionalActivityExecutionService(
            AdditionalActivityExecutionRepository executionRepository,
            AdditionalActivityRepository additionalActivityRepository,
            ArrangementTermRepository arrangementTermRepository,
            ActivityTermRepository activityTermRepository,
            AdditionalActivityPriceListRepository priceListRepository,
            UserRepository userRepository,
            AdditionalActivityRegistrationRepository registrationRepository
    ) {
        this.executionRepository = executionRepository;
        this.additionalActivityRepository = additionalActivityRepository;
        this.arrangementTermRepository = arrangementTermRepository;
        this.activityTermRepository = activityTermRepository;
        this.priceListRepository = priceListRepository;
        this.userRepository = userRepository;
        this.registrationRepository = registrationRepository;
    }

    public List<AdditionalActivityExecutionResponse> getByArrangementTerm(Long arrangementTermId) {

        List<AdditionalActivityExecutionResponse> activities =
                executionRepository.findByArrangementTermId(arrangementTermId)
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        if (activities.isEmpty()) {
            throw new BadRequestException("Nema dodatnih aktivnosti za ovu turu");
        }

        return activities;
    }

    public AdditionalActivityExecutionResponse createExecution(
            AdditionalActivityExecutionRequest request,
            Principal principal
    ) {
        User user = getAuthenticatedUser(principal);
        validateManager(user);
        validateRequest(request);

        ArrangementTerm arrangementTerm = arrangementTermRepository.findById(request.getArrangementTermId())
                .orElseThrow(() -> new BadRequestException("Arrangement term not found"));

        AdditionalActivity additionalActivity = additionalActivityRepository.findById(request.getAdditionalActivityId())
                .orElseThrow(() -> new BadRequestException("Additional activity not found"));

        ActivityTerm activityTerm = new ActivityTerm();
        activityTerm.setDate(request.getActivityDate());
        activityTerm.setStartTime(request.getStartTime());
        activityTerm = activityTermRepository.save(activityTerm);

        AdditionalActivityExecution execution = new AdditionalActivityExecution();
        execution.setArrangementTerm(arrangementTerm);
        execution.setAdditionalActivity(additionalActivity);
        execution.setActivityTerm(activityTerm);
        execution.setDurationMinutes(request.getDurationMinutes());
        execution.setCapacity(request.getCapacity());
        execution.setReservedSpots(0);

        execution = executionRepository.save(execution);

        AdditionalActivityPriceList priceList = new AdditionalActivityPriceList();
        priceList.setAdditionalActivityExecution(execution);
        priceList.setPrice(request.getPrice());
        priceList.setValidFrom(LocalDate.now());
        priceList.setValidTo(null);

        priceListRepository.save(priceList);

        return mapToResponse(execution);
    }

    @Transactional
    public void deleteExecution(Long id, Principal principal) {
        User user = getAuthenticatedUser(principal);
        validateManager(user);

        AdditionalActivityExecution execution = executionRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Activity execution not found"));

        registrationRepository.deleteByAdditionalActivityExecutionId(id);

        priceListRepository.deleteByAdditionalActivityExecutionId(id);

        executionRepository.delete(execution);
    }

    private void validateRequest(AdditionalActivityExecutionRequest request) {
        if (request.getArrangementTermId() == null) {
            throw new BadRequestException("Arrangement term is required");
        }

        if (request.getAdditionalActivityId() == null) {
            throw new BadRequestException("Additional activity is required");
        }

        if (request.getActivityDate() == null) {
            throw new BadRequestException("Activity date is required");
        }

        if (request.getStartTime() == null) {
            throw new BadRequestException("Start time is required");
        }

        if (request.getDurationMinutes() == null || request.getDurationMinutes() <= 0) {
            throw new BadRequestException("Duration must be greater than 0");
        }

        if (request.getCapacity() == null || request.getCapacity() <= 0) {
            throw new BadRequestException("Capacity must be greater than 0");
        }

        if (request.getPrice() == null || request.getPrice() < 0) {
            throw new BadRequestException("Price cannot be negative");
        }
    }

    private AdditionalActivityExecutionResponse mapToResponse(AdditionalActivityExecution execution) {
        AdditionalActivity activity = execution.getAdditionalActivity();

        double price = priceListRepository
                .findFirstByAdditionalActivityExecutionIdOrderByIdDesc(execution.getId())
                .map(AdditionalActivityPriceList::getPrice)
                .orElse(0.0);

        return new AdditionalActivityExecutionResponse(
                execution.getId(),
                execution.getArrangementTerm().getId(),
                activity.getId(),
                activity.getName(),
                activity.getDescription(),
                activity.getLocation(),
                activity.getImageUrl(),
                execution.getActivityTerm().getDate(),
                execution.getActivityTerm().getStartTime(),
                execution.getDurationMinutes(),
                execution.getCapacity(),
                execution.getReservedSpots(),
                execution.getAvailableSpots(),
                price
        );
    }

    private User getAuthenticatedUser(Principal principal) {
        if (principal == null || principal.getName() == null) {
            throw new BadRequestException("User is not authenticated");
        }

        return userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new BadRequestException("Authenticated user not found"));
    }

    private void validateManager(User user) {
        if (user.getRole() != Role.MANAGER) {
            throw new BadRequestException("Only MANAGER can manage activity executions");
        }
    }
}