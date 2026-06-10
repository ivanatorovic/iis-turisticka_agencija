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

import java.util.List;

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
        AdditionalActivity activity = findById(activityId);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new RuntimeException("Category not found with id: " + categoryId)
                );

        activity.getCategories().add(category);

        return additionalActivityRepository.save(activity);
    }

    @Override
    public AdditionalActivity removeCategoryFromActivity(Long activityId, Long categoryId) {
        AdditionalActivity activity = findById(activityId);

        boolean removed = activity.getCategories().removeIf(
                category -> category.getCategoryId() != null
                        && category.getCategoryId().equals(categoryId)
        );

        if (!removed) {
            throw new RuntimeException(
                    "Category not found on activity. Category id: " + categoryId
            );
        }

        return additionalActivityRepository.save(activity);
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
                arrangementDTO
        );
    }
}