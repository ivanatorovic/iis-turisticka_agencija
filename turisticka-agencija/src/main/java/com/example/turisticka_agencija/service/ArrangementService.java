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
        List<Arrangement> results = arrangementRepository.findAll()
                .stream()
                .filter(arrangement -> matchesDestination(arrangement, request.getDestination()))
                .filter(arrangement -> matchesTravelDate(arrangement, request.getTravelDate()))
                .filter(arrangement -> matchesBudget(arrangement, request))
                .filter(arrangement -> matchesAccommodationCategory(arrangement, request))
                .filter(arrangement -> matchesTransportType(arrangement, request))
                .filter(arrangement -> matchesNumberOfNights(arrangement, request))
                .filter(arrangement -> matchesAdditionalService(arrangement, request))
                .toList();

        if ("ASC".equalsIgnoreCase(request.getSortByPrice())) {
            return results.stream()
                    .sorted((a1, a2) -> Double.compare(a1.getBasePrice(), a2.getBasePrice()))
                    .toList();
        }

        if ("DESC".equalsIgnoreCase(request.getSortByPrice())) {
            return results.stream()
                    .sorted((a1, a2) -> Double.compare(a2.getBasePrice(), a1.getBasePrice()))
                    .toList();
        }

        return results;
    }

    private boolean matchesAccommodationCategory(Arrangement arrangement, ArrangementSearchRequest request) {
        if (request.getAccommodationCategory() == null) {
            return true;
        }

        return arrangement.getAccommodation() != null
                && arrangement.getAccommodation().getCategory() == request.getAccommodationCategory();
    }

    private boolean matchesTransportType(Arrangement arrangement, ArrangementSearchRequest request) {
        if (request.getTransportType() == null) {
            return true;
        }

        return arrangement.getTransport() != null
                && arrangement.getTransport().getType() == request.getTransportType();
    }

    private boolean matchesNumberOfNights(Arrangement arrangement, ArrangementSearchRequest request) {
        if (request.getNumberOfNights() == null) {
            return true;
        }

        return arrangement.getNumberOfNights() == request.getNumberOfNights();
    }

    private boolean matchesAdditionalService(Arrangement arrangement, ArrangementSearchRequest request) {
        if (request.getAdditionalService() == null || request.getAdditionalService().isBlank()) {
            return true;
        }

        return arrangement.getAdditionalServices()
                .stream()
                .anyMatch(service ->
                        service.getName().equalsIgnoreCase(request.getAdditionalService())
                );
    }

    private boolean matchesDestination(Arrangement arrangement, String destination) {
        if (destination == null || destination.isBlank()) {
            return true;
        }

        if (arrangement.getDestination() == null) {
            return false;
        }

        String search = destination.toLowerCase();

        return arrangement.getDestination().getName().toLowerCase().contains(search)
                || arrangement.getDestination().getCountry().toLowerCase().contains(search);
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

    public Arrangement getArrangementById(Long id) {
        return arrangementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Arrangement not found"));
    }
}