package com.example.turisticka_agencija.controller;

import com.example.turisticka_agencija.dto.AdditionalActivityResponse;
import com.example.turisticka_agencija.dto.AdditionalActivityShortResponse;
import com.example.turisticka_agencija.service.AdditionalActivityService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/additional-activities")
public class AdditionalActivityController {

    private final AdditionalActivityService additionalActivityService;

    public AdditionalActivityController(AdditionalActivityService additionalActivityService) {
        this.additionalActivityService = additionalActivityService;
    }

    @GetMapping
    public List<AdditionalActivityResponse> getAllAdditionalActivities() {
        return additionalActivityService.getAllAdditionalActivities();
    }

    @GetMapping("/{id}")
    public AdditionalActivityResponse getAdditionalActivityById(@PathVariable Long id) {
        return additionalActivityService.getAdditionalActivityById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AdditionalActivityResponse createAdditionalActivity(
            @RequestPart("info") String infoJson,
            @RequestPart("image") MultipartFile image,
            Principal principal
    ) {
        return additionalActivityService.createAdditionalActivity(infoJson, image, principal);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AdditionalActivityResponse updateAdditionalActivity(
            @PathVariable Long id,
            @RequestPart("info") String infoJson,
            @RequestPart(value = "image", required = false) MultipartFile image,
            Principal principal
    ) {
        return additionalActivityService.updateAdditionalActivity(id, infoJson, image, principal);
    }

    @DeleteMapping("/{id}")
    public void deleteAdditionalActivity(
            @PathVariable Long id,
            Principal principal
    ) {
        additionalActivityService.deleteAdditionalActivity(id, principal);
    }

    @GetMapping("/short")
    public List<AdditionalActivityShortResponse> getAllShortActivities() {
        return additionalActivityService.getAllShortActivities();
    }

    @PutMapping("/{activityId}/categories/{categoryId}")
    public AdditionalActivityResponse addCategoryToActivity(
            @PathVariable Long activityId,
            @PathVariable Long categoryId,
            Principal principal
    ) {
        return additionalActivityService.addCategoryToActivity(
                activityId,
                categoryId,
                principal
        );
    }

    @DeleteMapping("/{activityId}/categories/{categoryId}")
    public AdditionalActivityResponse removeCategoryFromActivity(
            @PathVariable Long activityId,
            @PathVariable Long categoryId,
            Principal principal
    ) {
        return additionalActivityService.removeCategoryFromActivity(
                activityId,
                categoryId,
                principal
        );
    }

}