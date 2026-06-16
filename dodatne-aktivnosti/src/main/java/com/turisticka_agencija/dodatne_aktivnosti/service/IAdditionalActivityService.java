package com.turisticka_agencija.dodatne_aktivnosti.service;

import com.turisticka_agencija.dodatne_aktivnosti.dto.AdditionalActivityDTO;
import com.turisticka_agencija.dodatne_aktivnosti.dto.RecommendedActivityDTO;
import com.turisticka_agencija.dodatne_aktivnosti.model.AdditionalActivity;

import java.util.List;

public interface IAdditionalActivityService {

    List<AdditionalActivity> findAll();

    AdditionalActivity findById(Long id);

    AdditionalActivity save(AdditionalActivity activity);

    AdditionalActivity update(Long id, AdditionalActivity activity);

    void delete(Long id);

    AdditionalActivity addCategoryToActivity(Long activityId, Long categoryId);

    AdditionalActivity removeCategoryFromActivity(Long activityId, Long categoryId);

    List<RecommendedActivityDTO> recommendActivitiesBySimilarCustomers(Long customerId);
    List<RecommendedActivityDTO> recommendActivitiesByCategory(Long customerId);
    List<RecommendedActivityDTO> findAffordableActivitiesForCustomer(Long customerId);
    List<RecommendedActivityDTO> findPopularActivitiesForCustomer(Long customerId);
    List<RecommendedActivityDTO> recommendBestActivities(Long customerId);
    List<RecommendedActivityDTO> recommendBestActivitiesForArrangement(
            Long customerId,
            Long arrangementId
    );
}