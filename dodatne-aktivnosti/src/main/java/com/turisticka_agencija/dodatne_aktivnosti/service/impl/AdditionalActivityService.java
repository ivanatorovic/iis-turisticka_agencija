package com.turisticka_agencija.dodatne_aktivnosti.service.impl;

import com.turisticka_agencija.dodatne_aktivnosti.dto.ArrangementDTO;
import com.turisticka_agencija.dodatne_aktivnosti.dto.RecommendedActivityDTO;
import com.turisticka_agencija.dodatne_aktivnosti.model.AdditionalActivity;
import com.turisticka_agencija.dodatne_aktivnosti.model.AdditionalActivityExecution;
import com.turisticka_agencija.dodatne_aktivnosti.model.Category;
import com.turisticka_agencija.dodatne_aktivnosti.repository.AdditionalActivityExecutionRepository;
import com.turisticka_agencija.dodatne_aktivnosti.repository.AdditionalActivityRepository;
import com.turisticka_agencija.dodatne_aktivnosti.repository.CategoryRepository;
import com.turisticka_agencija.dodatne_aktivnosti.service.IAdditionalActivityService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdditionalActivityService implements IAdditionalActivityService {

    private final AdditionalActivityRepository additionalActivityRepository;
    private final CategoryRepository categoryRepository;
    private final AdditionalActivityExecutionRepository executionRepository;

    public AdditionalActivityService(
            AdditionalActivityRepository additionalActivityRepository,
            CategoryRepository categoryRepository,
            AdditionalActivityExecutionRepository executionRepository
    ) {
        this.additionalActivityRepository = additionalActivityRepository;
        this.categoryRepository = categoryRepository;
        this.executionRepository = executionRepository;
    }

    @Override
    public List<AdditionalActivity> findAll() {
        return additionalActivityRepository.findAll();
    }

    @Override
    public AdditionalActivity findById(Long id) {
        return additionalActivityRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Additional activity not found with id: " + id)
                );
    }

    @Override
    public AdditionalActivity save(AdditionalActivity activity) {
        return additionalActivityRepository.save(activity);
    }

    @Override
    public AdditionalActivity update(Long id, AdditionalActivity activity) {
        AdditionalActivity existingActivity = findById(id);

        if (activity.getName() != null) {
            existingActivity.setName(activity.getName());
        }

        if (activity.getDescription() != null) {
            existingActivity.setDescription(activity.getDescription());
        }

        if (activity.getType() != null) {
            existingActivity.setType(activity.getType());
        }

        if (activity.getLocation() != null) {
            existingActivity.setLocation(activity.getLocation());
        }

        if (activity.getImageUrl() != null) {
            existingActivity.setImageUrl(activity.getImageUrl());
        }

        if (activity.getCategories() != null && !activity.getCategories().isEmpty()) {
            existingActivity.setCategories(activity.getCategories());
        }

        return additionalActivityRepository.save(existingActivity);
    }

    @Override
    public void delete(Long id) {
        additionalActivityRepository.deleteById(id);
    }

    @Override
    public AdditionalActivity addCategoryToActivity(Long activityId, Long categoryId) {
        Long matched = additionalActivityRepository.addCategoryRelation(activityId, categoryId);

        if (matched == null || matched == 0) {
            throw new RuntimeException(
                    "Activity or category not found. activityId: "
                            + activityId + ", categoryId: " + categoryId
            );
        }

        return null;
    }

    @Override
    public AdditionalActivity removeCategoryFromActivity(Long activityId, Long categoryId) {
        Long matched = additionalActivityRepository.removeCategoryRelation(activityId, categoryId);

        if (matched == null || matched == 0) {
            throw new RuntimeException(
                    "Activity or category not found. activityId: "
                            + activityId + ", categoryId: " + categoryId
            );
        }

        return null;
    }

    @Override
    public List<RecommendedActivityDTO> recommendActivitiesBySimilarCustomers(Long customerId) {
        return executionRepository.recommendBySimilarCustomers(customerId)
                .stream()
                .map(this::mapExecutionToDTO)
                .toList();
    }

    @Override
    public List<RecommendedActivityDTO> recommendActivitiesByCategory(Long customerId) {
        return executionRepository.recommendByCategory(customerId)
                .stream()
                .map(this::mapExecutionToDTO)
                .toList();
    }

    @Override
    public List<RecommendedActivityDTO> findAffordableActivitiesForCustomer(Long customerId) {
        return executionRepository.findAffordableForCustomer(customerId)
                .stream()
                .map(this::mapExecutionToDTO)
                .toList();
    }

    @Override
    public List<RecommendedActivityDTO> findPopularActivitiesForCustomer(Long customerId) {
        return executionRepository.findPopularForCustomer(customerId)
                .stream()
                .map(this::mapExecutionToDTO)
                .toList();
    }

    @Override
    public List<RecommendedActivityDTO> recommendBestActivities(Long customerId) {

        Map<Long, RecommendedActivityDTO> activities = new HashMap<>();
        Map<Long, Integer> scores = new HashMap<>();

        List<RecommendedActivityDTO> similar = recommendActivitiesBySimilarCustomers(customerId);
        List<RecommendedActivityDTO> category = recommendActivitiesByCategory(customerId);
        List<RecommendedActivityDTO> popular = findPopularActivitiesForCustomer(customerId);
        List<RecommendedActivityDTO> affordable = findAffordableActivitiesForCustomer(customerId);

        addRecommendationPoints(activities, scores, similar, 55);
        addRecommendationPoints(activities, scores, category, 33);
        addRecommendationPoints(activities, scores, popular, 22);
        addRecommendationPoints(activities, scores, affordable, 11);

        List<RecommendedActivityDTO> result = new ArrayList<>(activities.values());

        result.sort((first, second) -> compareRecommendations(first, second, scores));

        result.forEach(recommendation ->
                recommendation.setScore(
                        scores.getOrDefault(
                                recommendation.getExecutionId(),
                                0
                        )
                )
        );
        return result;
    }

    private void addRecommendationPoints(
            Map<Long, RecommendedActivityDTO> activities,
            Map<Long, Integer> scores,
            List<RecommendedActivityDTO> recommendations,
            int points
    ) {
        for (RecommendedActivityDTO recommendation : recommendations) {
            Long executionId = recommendation.getExecutionId();

            activities.putIfAbsent(executionId, recommendation);

            int currentScore = scores.getOrDefault(executionId, 0);
            scores.put(executionId, currentScore + points);
        }
    }

    private int compareRecommendations(
            RecommendedActivityDTO first,
            RecommendedActivityDTO second,
            Map<Long, Integer> scores
    ) {
        int firstScore = scores.getOrDefault(first.getExecutionId(), 0);
        int secondScore = scores.getOrDefault(second.getExecutionId(), 0);

        return Integer.compare(secondScore, firstScore);
    }

    public List<RecommendedActivityDTO> recommendBestActivitiesForArrangement(
            Long customerId,
            Long arrangementId
    ) {
        return recommendBestActivities(customerId)
                .stream()
                .filter(activity ->
                        activity.getArrangement() != null &&
                                activity.getArrangement().getId().equals(arrangementId)
                )
                .toList();
    }

    private RecommendedActivityDTO mapExecutionToDTO(AdditionalActivityExecution execution) {
        AdditionalActivity activity = execution.getActivity();

        ArrangementDTO arrangementDTO = null;

        if (execution.getArrangement() != null) {
            arrangementDTO = new ArrangementDTO(
                    execution.getArrangement().getArrangementId(),
                    execution.getArrangement().getName()
            );
        }

        return new RecommendedActivityDTO(
                execution.getExecutionId(),

                activity != null ? activity.getActivityId() : null,
                activity != null ? activity.getName() : null,
                activity != null ? activity.getDescription() : null,
                activity != null ? activity.getLocation() : null,

                execution.getActivityDate(),
                execution.getStartTime(),
                execution.getDurationMinutes(),
                execution.getCapacity(),
                execution.getReservedSpots(),
                execution.getPrice(),
                execution.getStatus(),
                execution.getPrior(),
                arrangementDTO,
                0
        );
    }
}