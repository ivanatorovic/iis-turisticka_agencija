package com.example.turisticka_agencija.service;

import com.example.turisticka_agencija.dto.ArrangementTermResponseDto;
import com.example.turisticka_agencija.model.ArrangementTerm;
import com.example.turisticka_agencija.repository.ArrangementTermRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArrangementTermService {

    private final ArrangementTermRepository arrangementTermRepository;
    private final DynamicPricingService dynamicPricingService;

    public ArrangementTermService(ArrangementTermRepository arrangementTermRepository,
                                  DynamicPricingService dynamicPricingService) {
        this.arrangementTermRepository = arrangementTermRepository;
        this.dynamicPricingService = dynamicPricingService;
    }

    public List<ArrangementTermResponseDto> getAllArrangementTerms() {
        return arrangementTermRepository.findAllWithArrangementAndTerm()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    private ArrangementTermResponseDto mapToResponseDto(ArrangementTerm arrangementTerm) {
        int availableSpots = arrangementTerm.getCapacity() - arrangementTerm.getReservedSpots();
        double dynamicPrice = dynamicPricingService.calculatePrice(
                arrangementTerm.getArrangement(),
                arrangementTerm
        );

        return new ArrangementTermResponseDto(
                arrangementTerm.getId(),

                arrangementTerm.getArrangement().getId(),
                arrangementTerm.getArrangement().getName(),
                arrangementTerm.getArrangement().getDestination().getName(),
                arrangementTerm.getArrangement().getDestination().getCountry(),
                arrangementTerm.getArrangement().getBasePrice(),

                arrangementTerm.getArrangement().getNumberOfNights(),

                arrangementTerm.getTerm().getId(),
                arrangementTerm.getTerm().getStartDate(),
                arrangementTerm.getTerm().getEndDate(),

                arrangementTerm.getCapacity(),
                arrangementTerm.getReservedSpots(),
                availableSpots,
                dynamicPrice

        );
    }
}