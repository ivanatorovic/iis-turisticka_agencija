package com.example.turisticka_agencija.service;

import com.example.turisticka_agencija.dto.ArrangementSearchRequest;
import com.example.turisticka_agencija.model.Arrangement;
import com.example.turisticka_agencija.model.Term;
import com.example.turisticka_agencija.repository.ArrangementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArrangementService {

    private final ArrangementRepository arrangementRepository;

    public ArrangementService(ArrangementRepository arrangementRepository) {
        this.arrangementRepository = arrangementRepository;
    }

    public List<Arrangement> getAllArrangements() {
        return arrangementRepository.findAll();
    }

    public Arrangement createArrangement(Arrangement arrangement) {
        return arrangementRepository.save(arrangement);
    }

    public List<Arrangement> searchArrangements(ArrangementSearchRequest request) {
        return arrangementRepository.findAll()
                .stream()
                .filter(arrangement -> matchesDestination(arrangement, request.getDestination()))
                .filter(arrangement -> matchesTravelDate(arrangement, request.getTravelDate()))
                .filter(arrangement -> matchesBudget(arrangement, request))
                .toList();
    }

    private boolean matchesDestination(Arrangement arrangement, String destination) {
        if (destination == null || destination.isBlank()) {
            return true;
        }

        return arrangement.getDestination() != null &&
                arrangement.getDestination()
                        .toLowerCase()
                        .contains(destination.toLowerCase());
    }

    private boolean matchesTravelDate(Arrangement arrangement, java.time.LocalDate travelDate) {
        if (travelDate == null) {
            return true;
        }

        return arrangement.getTerms()
                .stream()
                .anyMatch(term -> isDateInsideTerm(term, travelDate));
    }

    private boolean isDateInsideTerm(Term term, java.time.LocalDate travelDate) {
        return !travelDate.isBefore(term.getStartDate())
                && !travelDate.isAfter(term.getEndDate());
    }

    private boolean matchesBudget(Arrangement arrangement, ArrangementSearchRequest request) {
        if (request.getBudget() == null) {
            return true;
        }

        int passengers = request.getNumberOfPassengers() > 0
                ? request.getNumberOfPassengers()
                : 1;

        double totalPrice = arrangement.getBasePrice() * passengers;

        return totalPrice <= request.getBudget();
    }
}