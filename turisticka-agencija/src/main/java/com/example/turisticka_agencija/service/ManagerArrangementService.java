package com.example.turisticka_agencija.service;

import com.example.turisticka_agencija.dto.ManagerArrangementRequestDto;
import com.example.turisticka_agencija.model.*;
import com.example.turisticka_agencija.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerArrangementService {

    private final ManagerArrangementRepository managerArrangementRepository;
    private final WorkflowRepository workflowRepository;
    private final UserRepository userRepository;
    private final DestinationRepository destinationRepository;
    private final AccommodationRepository accommodationRepository;
    private final TransportRepository transportRepository;
    private final ArrangementRepository arrangementRepository;

    public ManagerArrangementService(
            ManagerArrangementRepository managerArrangementRepository,
            WorkflowRepository workflowRepository,
            UserRepository userRepository,
            DestinationRepository destinationRepository,
            AccommodationRepository accommodationRepository,
            TransportRepository transportRepository,
            ArrangementRepository arrangementRepository
    ) {
        this.managerArrangementRepository = managerArrangementRepository;
        this.workflowRepository = workflowRepository;
        this.userRepository = userRepository;
        this.destinationRepository = destinationRepository;
        this.accommodationRepository = accommodationRepository;
        this.transportRepository = transportRepository;
        this.arrangementRepository = arrangementRepository;
    }

    public List<ManagerArrangement> getByManager(Long managerId) {
        return managerArrangementRepository.findByManagerId(managerId);
    }

    public List<ManagerArrangement> getPendingForDirector() {
        return managerArrangementRepository.findByStatus(
                ManagerArrangementStatus.SENT_TO_DIRECTOR
        );
    }

    public ManagerArrangement getById(Long id) {
        return managerArrangementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manager arrangement not found"));
    }

    public ManagerArrangement create(ManagerArrangementRequestDto request) {
        validateRequest(request);

        Workflow workflow = workflowRepository.findById(request.getWorkflowId())
                .orElseThrow(() -> new RuntimeException("Workflow not found"));

        User manager = userRepository.findById(request.getManagerId())
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        Destination destination = destinationRepository.findById(request.getDestinationId())
                .orElseThrow(() -> new RuntimeException("Destination not found"));

        Accommodation accommodation = accommodationRepository.findById(request.getAccommodationId())
                .orElseThrow(() -> new RuntimeException("Accommodation not found"));

        Transport transport = transportRepository.findById(request.getTransportId())
                .orElseThrow(() -> new RuntimeException("Transport not found"));

        ManagerArrangement arrangement = new ManagerArrangement();
        arrangement.setName(request.getName());
        arrangement.setDescription(request.getDescription());
        arrangement.setBasePrice(request.getBasePrice());
        arrangement.setNumberOfNights(request.getNumberOfNights());
        arrangement.setImageUrl(request.getImageUrl());

        arrangement.setWorkflow(workflow);
        arrangement.setManager(manager);
        arrangement.setDestination(destination);
        arrangement.setAccommodation(accommodation);
        arrangement.setTransport(transport);
        arrangement.setStatus(ManagerArrangementStatus.DRAFT);

        return managerArrangementRepository.save(arrangement);
    }

    public ManagerArrangement update(Long id, ManagerArrangementRequestDto request) {
        validateRequest(request);

        ManagerArrangement arrangement = getById(id);

        Destination destination = destinationRepository.findById(request.getDestinationId())
                .orElseThrow(() -> new RuntimeException("Destination not found"));

        Accommodation accommodation = accommodationRepository.findById(request.getAccommodationId())
                .orElseThrow(() -> new RuntimeException("Accommodation not found"));

        Transport transport = transportRepository.findById(request.getTransportId())
                .orElseThrow(() -> new RuntimeException("Transport not found"));

        arrangement.setName(request.getName());
        arrangement.setDescription(request.getDescription());
        arrangement.setBasePrice(request.getBasePrice());
        arrangement.setNumberOfNights(request.getNumberOfNights());
        arrangement.setImageUrl(request.getImageUrl());

        arrangement.setDestination(destination);
        arrangement.setAccommodation(accommodation);
        arrangement.setTransport(transport);

        return managerArrangementRepository.save(arrangement);
    }

    public ManagerArrangement sendToDirector(Long id) {
        ManagerArrangement arrangement = getById(id);

        validateCompletedArrangement(arrangement);

        arrangement.setStatus(ManagerArrangementStatus.SENT_TO_DIRECTOR);

        return managerArrangementRepository.save(arrangement);
    }

    public ManagerArrangement approve(Long id) {
        ManagerArrangement managerArrangement = getById(id);

        validateCompletedArrangement(managerArrangement);

        managerArrangement.setStatus(ManagerArrangementStatus.APPROVED);

        Arrangement publishedArrangement = new Arrangement();
        publishedArrangement.setName(managerArrangement.getName());
        publishedArrangement.setDescription(managerArrangement.getDescription());
        publishedArrangement.setBasePrice(managerArrangement.getBasePrice());
        publishedArrangement.setNumberOfNights(managerArrangement.getNumberOfNights());
        publishedArrangement.setImageUrl(managerArrangement.getImageUrl());
        publishedArrangement.setDestination(managerArrangement.getDestination());
        publishedArrangement.setAccommodation(managerArrangement.getAccommodation());
        publishedArrangement.setTransport(managerArrangement.getTransport());

        arrangementRepository.save(publishedArrangement);

        managerArrangement.setStatus(ManagerArrangementStatus.PUBLISHED);

        return managerArrangementRepository.save(managerArrangement);
    }

    public ManagerArrangement reject(Long id) {
        ManagerArrangement arrangement = getById(id);

        arrangement.setStatus(ManagerArrangementStatus.REJECTED);

        return managerArrangementRepository.save(arrangement);
    }

    public void delete(Long id) {
        managerArrangementRepository.deleteById(id);
    }

    private void validateRequest(ManagerArrangementRequestDto request) {
        if (request.getName() == null || request.getName().isBlank()) {
            throw new RuntimeException("Naziv aranžmana je obavezan.");
        }

        if (request.getDescription() == null || request.getDescription().isBlank()) {
            throw new RuntimeException("Opis aranžmana je obavezan.");
        }

        if (request.getBasePrice() <= 0) {
            throw new RuntimeException("Cena mora biti veća od 0.");
        }

        if (request.getNumberOfNights() <= 0) {
            throw new RuntimeException("Broj noćenja mora biti veći od 0.");
        }

        if (request.getWorkflowId() == null) {
            throw new RuntimeException("Radni tok je obavezan.");
        }

        if (request.getManagerId() == null) {
            throw new RuntimeException("Menadžer je obavezan.");
        }

        if (request.getDestinationId() == null) {
            throw new RuntimeException("Destinacija je obavezna.");
        }

        if (request.getAccommodationId() == null) {
            throw new RuntimeException("Smeštaj je obavezan.");
        }

        if (request.getTransportId() == null) {
            throw new RuntimeException("Prevoz je obavezan.");
        }
    }

    private void validateCompletedArrangement(ManagerArrangement arrangement) {
        if (arrangement.getName() == null || arrangement.getName().isBlank()) {
            throw new RuntimeException("Naziv aranžmana je obavezan.");
        }

        if (arrangement.getDescription() == null || arrangement.getDescription().isBlank()) {
            throw new RuntimeException("Opis aranžmana je obavezan.");
        }

        if (arrangement.getBasePrice() <= 0) {
            throw new RuntimeException("Cena mora biti veća od 0.");
        }

        if (arrangement.getNumberOfNights() <= 0) {
            throw new RuntimeException("Broj noćenja mora biti veći od 0.");
        }

        if (arrangement.getDestination() == null) {
            throw new RuntimeException("Destinacija je obavezna.");
        }

        if (arrangement.getAccommodation() == null) {
            throw new RuntimeException("Smeštaj je obavezan.");
        }

        if (arrangement.getTransport() == null) {
            throw new RuntimeException("Prevoz je obavezan.");
        }
    }
}