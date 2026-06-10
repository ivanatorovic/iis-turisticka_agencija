package com.turisticka_agencija.dodatne_aktivnosti.controller;

import com.turisticka_agencija.dodatne_aktivnosti.dto.AdditionalActivityDTO;
import com.turisticka_agencija.dodatne_aktivnosti.dto.RecommendedActivityDTO;
import com.turisticka_agencija.dodatne_aktivnosti.model.AdditionalActivity;
import com.turisticka_agencija.dodatne_aktivnosti.service.IAdditionalActivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activities")
public class AdditionalActivityController {

    private final IAdditionalActivityService additionalActivityService;

    public AdditionalActivityController(IAdditionalActivityService additionalActivityService) {
        this.additionalActivityService = additionalActivityService;
    }

    @GetMapping
    public ResponseEntity<List<AdditionalActivity>> getAllActivities() {
        return new ResponseEntity<>(additionalActivityService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdditionalActivity> getActivityById(@PathVariable Long id) {
        return new ResponseEntity<>(additionalActivityService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AdditionalActivity> createActivity(@RequestBody AdditionalActivity additionalActivity) {
        return new ResponseEntity<>(additionalActivityService.save(additionalActivity), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdditionalActivity> updateActivity(@PathVariable Long id,
                                                             @RequestBody AdditionalActivity additionalActivity) {
        return new ResponseEntity<>(additionalActivityService.update(id, additionalActivity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        additionalActivityService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{activityId}/categories/{categoryId}")
    public ResponseEntity<AdditionalActivity> addCategoryToActivity(@PathVariable Long activityId,
                                                                    @PathVariable Long categoryId) {
        return new ResponseEntity<>(
                additionalActivityService.addCategoryToActivity(activityId, categoryId),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{activityId}/categories/{categoryId}")
    public ResponseEntity<AdditionalActivity> removeCategoryFromActivity(@PathVariable Long activityId,
                                                                         @PathVariable Long categoryId) {
        return new ResponseEntity<>(
                additionalActivityService.removeCategoryFromActivity(activityId, categoryId),
                HttpStatus.OK
        );
    }

    @GetMapping("/recommendations/similar-customers/{customerId}")
    public ResponseEntity<List<RecommendedActivityDTO>> recommendActivitiesBySimilarCustomers(@PathVariable Long customerId) {
        return new ResponseEntity<>(
                additionalActivityService.recommendActivitiesBySimilarCustomers(customerId),
                HttpStatus.OK
        );
    }

    @GetMapping("/recommendations/category/{customerId}")
    public ResponseEntity<List<RecommendedActivityDTO>> recommendActivitiesByCategory(@PathVariable Long customerId) {
        return new ResponseEntity<>(
                additionalActivityService.recommendActivitiesByCategory(customerId),
                HttpStatus.OK
        );
    }

    @GetMapping("/recommendations/affordable/{customerId}")
    public ResponseEntity<List<RecommendedActivityDTO>> findAffordableActivitiesForCustomer(@PathVariable Long customerId) {
        return new ResponseEntity<>(
                additionalActivityService.findAffordableActivitiesForCustomer(customerId),
                HttpStatus.OK
        );
    }

    @GetMapping("/recommendations/popular/{customerId}")
    public ResponseEntity<List<RecommendedActivityDTO>> findPopularActivitiesForCustomer(@PathVariable Long customerId) {
        return new ResponseEntity<>(
                additionalActivityService.findPopularActivitiesForCustomer(customerId),
                HttpStatus.OK
        );
    }
}